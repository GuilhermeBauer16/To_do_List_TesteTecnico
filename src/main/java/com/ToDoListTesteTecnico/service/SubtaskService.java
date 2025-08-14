package com.ToDoListTesteTecnico.service;

import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.SubtaskEntity;
import com.ToDoListTesteTecnico.entity.UserEntity;
import com.ToDoListTesteTecnico.entity.values.SubtaskVO;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.entity.values.UserVO;
import com.ToDoListTesteTecnico.exception.subtask.InvalidSubTaskException;
import com.ToDoListTesteTecnico.exception.subtask.InvalidSubTaskStatusException;
import com.ToDoListTesteTecnico.exception.subtask.SubTaskNotFoundException;
import com.ToDoListTesteTecnico.exception.utils.FieldNotFoundException;
import com.ToDoListTesteTecnico.factory.SubtaskFactory;
import com.ToDoListTesteTecnico.mapper.BuilderMapper;
import com.ToDoListTesteTecnico.repository.SubtaskRepository;
import com.ToDoListTesteTecnico.request.UpdateStatusRequest;
import com.ToDoListTesteTecnico.service.contract.SubTaskServiceContract;
import com.ToDoListTesteTecnico.service.user.UserService;
import com.ToDoListTesteTecnico.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SubtaskService implements SubTaskServiceContract {

    private final TaskService taskService;
    private final SubtaskRepository repository;
    private final UserService userService;


    private static final String SUBTASK_NOT_FOUND_EXCEPTION_MESSAGE = "This Subtask was not found";
    private static final String INVALID_SUBTASK_EXCEPTION_MESSAGE = "This Subtask is invalid, please verify the field and try again";
    private static final String INVALID_SUBTASK_STATUS_EXCEPTION_MESSAGE = "Is not possible create a Subtask with completed status";

    @Autowired
    public SubtaskService(TaskService taskService, SubtaskRepository subtaskRepository, UserService userService) {
        this.taskService = taskService;
        this.repository = subtaskRepository;
        this.userService = userService;
    }


    @Override

    public SubtaskEntity createSubtask(SubtaskVO subtaskVO) {

        ValidatorUtils.checkObjectIsNullOrThrowException(subtaskVO, INVALID_SUBTASK_EXCEPTION_MESSAGE, InvalidSubTaskException.class);
        UserVO userByEmail = userService.findUserByEmail(retrieveUserEmail());
        UserEntity userEntity = BuilderMapper.parseObject(new UserEntity(), userByEmail);
        if (subtaskVO.getStatus() == Status.COMPLETED) {
            throw new InvalidSubTaskStatusException(INVALID_SUBTASK_STATUS_EXCEPTION_MESSAGE);
        }


        SubtaskEntity subtaskEntity = SubtaskFactory.createSubtask(subtaskVO.getTitle(), subtaskVO.getDescription(),
                subtaskVO.getDueDate(), subtaskVO.getStatus(), subtaskVO.getPriority(), userEntity);

        ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(subtaskEntity, INVALID_SUBTASK_EXCEPTION_MESSAGE, FieldNotFoundException.class);

        return repository.save(subtaskEntity);

    }

    @Override

    public TaskVO addSubTaskToTask(String taskId, SubtaskVO subtaskVO) {


        TaskVO taskVO = taskService.findTaskById(taskId);

        SubtaskEntity subtask = createSubtask(subtaskVO);
        taskVO.getSubTasks().add(subtask);


        return taskService.updateTask(taskVO);
    }

    @Override
    public SubtaskVO updateSubTaskStatus(String id, UpdateStatusRequest updateStatusRequest) {

        SubtaskEntity subtaskEntity = repository.findByIdAndUserEmail(id, retrieveUserEmail())
                .orElseThrow(() -> new SubTaskNotFoundException(SUBTASK_NOT_FOUND_EXCEPTION_MESSAGE));
        subtaskEntity.setStatus(updateStatusRequest.getStatus());
        SubtaskEntity updatedSubtaskEntity = ValidatorUtils.updateFieldIfNotNull(new SubtaskEntity(),
                subtaskEntity, INVALID_SUBTASK_EXCEPTION_MESSAGE, FieldNotFoundException.class);

        repository.save(updatedSubtaskEntity);
        return BuilderMapper.parseObject(new SubtaskVO(), updatedSubtaskEntity);
    }


    @Override
    public SubtaskVO findSubTaskById(String id) {

        SubtaskEntity subtaskEntity = repository.findByIdAndUserEmail(id, retrieveUserEmail()).orElseThrow(() -> new SubTaskNotFoundException(SUBTASK_NOT_FOUND_EXCEPTION_MESSAGE));
        return BuilderMapper.parseObject(new SubtaskVO(), subtaskEntity);
    }

    @Override
    public void deleteSubTask(String id) {

        SubtaskEntity subtaskEntity = repository.findByIdAndUserEmail(id, retrieveUserEmail())
                .orElseThrow(() -> new SubTaskNotFoundException(SUBTASK_NOT_FOUND_EXCEPTION_MESSAGE));

        repository.delete(subtaskEntity);

    }

    private String retrieveUserEmail() {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUsername();

    }


}
