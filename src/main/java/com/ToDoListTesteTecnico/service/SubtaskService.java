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
import com.ToDoListTesteTecnico.response.TaskResponse;
import com.ToDoListTesteTecnico.service.contract.SubTaskServiceContract;
import com.ToDoListTesteTecnico.service.user.UserService;
import com.ToDoListTesteTecnico.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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
    @Transactional
    public TaskResponse addSubTaskToTask(String taskId, SubtaskVO subtaskVO) {


        TaskResponse taskVO = taskService.findTaskById(taskId);

        SubtaskEntity subtask = createSubtask(subtaskVO);
        taskVO.getSubTasks().add(subtask);
        TaskVO taskVO1 = BuilderMapper.parseObject(new TaskVO(), taskVO);
        TaskResponse taskResponse = taskService.updateTask(taskVO1);
        return taskResponse;
    }

    @Override
    public SubtaskVO updateSubTaskStatus(String id, UpdateStatusRequest updateStatusRequest) {

        SubtaskVO subtaskVO = findSubTaskById(id);
        subtaskVO.setStatus(updateStatusRequest.getStatus());
        SubtaskEntity subtaskEntity = ValidatorUtils.updateFieldIfNotNull(new SubtaskEntity(),
                subtaskVO, INVALID_SUBTASK_EXCEPTION_MESSAGE, FieldNotFoundException.class);

        repository.save(subtaskEntity);
        return BuilderMapper.parseObject(new SubtaskVO(), subtaskEntity);
    }


    @Override
    public SubtaskVO findSubTaskById(String id) {

        SubtaskEntity subtaskEntity = repository.findByIdAndUserEmail(id, retrieveUserEmail()).orElseThrow(() -> new SubTaskNotFoundException(SUBTASK_NOT_FOUND_EXCEPTION_MESSAGE));
        return BuilderMapper.parseObject(new SubtaskVO(), subtaskEntity);
    }

    @Override
    public void deleteSubTask(String id) {

        SubtaskVO subTaskById = findSubTaskById(id);
        SubtaskEntity subtaskEntity = BuilderMapper.parseObject(new SubtaskEntity(), subTaskById);
        repository.delete(subtaskEntity);

    }

    private String retrieveUserEmail() {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUsername();

    }


}
