package com.ToDoListTesteTecnico.service;

import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.SubtaskEntity;
import com.ToDoListTesteTecnico.entity.values.SubtaskVO;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.exception.FieldNotFoundException;
import com.ToDoListTesteTecnico.exception.TaskNotFoundException;
import com.ToDoListTesteTecnico.factory.SubtaskFactory;
import com.ToDoListTesteTecnico.mapper.BuilderMapper;
import com.ToDoListTesteTecnico.repository.SubtaskRepository;
import com.ToDoListTesteTecnico.request.UpdateStatusRequest;
import com.ToDoListTesteTecnico.service.contract.SubTaskServiceContract;
import com.ToDoListTesteTecnico.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtaskService implements SubTaskServiceContract {

    private final TaskService taskService;
    private final SubtaskRepository repository;

    private static final String TASK_NOT_FOUND_EXCEPTION_MESSAGE = "This Task was not found";

    @Autowired
    public SubtaskService(TaskService taskService, SubtaskRepository subtaskRepository) {
        this.taskService = taskService;
        this.repository = subtaskRepository;

    }


    @Override
    public SubtaskVO createSubtask(SubtaskVO subtaskVO) {

        ValidatorUtils.checkObjectIsNullOrThrowException(subtaskVO, TASK_NOT_FOUND_EXCEPTION_MESSAGE, TaskNotFoundException.class);

        if(subtaskVO.getStatus() == Status.COMPLETED){
            throw new RuntimeException("Is not possible create a task with completed status");
        }
        SubtaskEntity subtaskEntity = SubtaskFactory.createSubtask(subtaskVO.getTitle(), subtaskVO.getDescription(),
                subtaskVO.getDueDate(), subtaskVO.getStatus(), subtaskVO.getPriority());

        ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(subtaskEntity, TASK_NOT_FOUND_EXCEPTION_MESSAGE, FieldNotFoundException.class);
        repository.save(subtaskEntity);
        return BuilderMapper.parseObject(new SubtaskVO(), subtaskEntity);

    }

    @Override
    public TaskVO addSubTaskToTask(String taskId, SubtaskVO subtaskVO) {


        TaskVO taskVO = taskService.findTaskById(taskId);

        SubtaskVO subtask = createSubtask(subtaskVO);
        SubtaskEntity subtaskEntity = BuilderMapper.parseObject(new SubtaskEntity(), subtask);
        verifyIfNotHaveDuplicatedSubtask(subtask.getId(), taskVO);
        taskVO.getSubTasks().add(subtaskEntity);

        return taskService.updateTask(taskVO);
    }

    @Override
    public SubtaskVO updateSubTaskStatus(String id, UpdateStatusRequest updateStatusRequest) {

        SubtaskVO subtaskVO = findSubTaskById(id);
        subtaskVO.setStatus(updateStatusRequest.getStatus());
        SubtaskEntity subtaskEntity = ValidatorUtils.updateFieldIfNotNull(new SubtaskEntity(),
                subtaskVO, TASK_NOT_FOUND_EXCEPTION_MESSAGE, FieldNotFoundException.class);

        repository.save(subtaskEntity);
        return BuilderMapper.parseObject(new SubtaskVO(), subtaskEntity);
    }


    @Override
    public SubtaskVO findSubTaskById(String id) {

        SubtaskEntity subtaskEntity = repository.findById(id).orElseThrow(() -> new TaskNotFoundException(TASK_NOT_FOUND_EXCEPTION_MESSAGE));
        return BuilderMapper.parseObject(new SubtaskVO(), subtaskEntity);
    }

    @Override
    public void deleteSubTask(String id) {

        SubtaskVO subTaskById = findSubTaskById(id);
        SubtaskEntity subtaskEntity = BuilderMapper.parseObject(new SubtaskEntity(), subTaskById);
        repository.delete(subtaskEntity);

    }


    private void verifyIfNotHaveDuplicatedSubtask(String subTaskId, TaskVO taskVO) {

        List<SubtaskEntity> subTasks = taskVO.getSubTasks();

        for (int i = 0; i < subTasks.size(); i++) {

            if (subTaskId.equals(subTasks.get(i).getId())) {

                throw new RuntimeException("This subtask is already associated with this task!");
            }


        }
    }
}
