package com.ToDoListTesteTecnico.service;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.SubtaskEntity;
import com.ToDoListTesteTecnico.entity.TaskEntity;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.exception.utils.FieldNotFoundException;
import com.ToDoListTesteTecnico.exception.task.InvalidTaskException;
import com.ToDoListTesteTecnico.exception.task.InvalidTaskStatusException;
import com.ToDoListTesteTecnico.exception.subtask.SubTaskNotCompletedException;
import com.ToDoListTesteTecnico.exception.task.TaskNotFoundException;
import com.ToDoListTesteTecnico.factory.TaskFactory;
import com.ToDoListTesteTecnico.mapper.BuilderMapper;
import com.ToDoListTesteTecnico.repository.TaskRepository;
import com.ToDoListTesteTecnico.request.UpdateStatusRequest;
import com.ToDoListTesteTecnico.service.contract.TaskServiceContract;
import com.ToDoListTesteTecnico.utils.TaskSpecificatios;
import com.ToDoListTesteTecnico.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService implements TaskServiceContract {

    private static final String TASK_NOT_FOUND_EXCEPTION_MESSAGE = "This Task was not found";
    private static final String INVALID_TASK_EXCEPTION_MESSAGE = "This Task is invalid, please verify the field and try again";
    private static final String SUBTASK_NOT_COMPLETED_EXCEPTION_MESSAGE = "Is not possible updated a task with subtasks not completed yet";
    private static final String INVALID_TASK_STATUS_EXCEPTION_MESSAGE = "Is not possible create a task with completed status";


    private final TaskRepository repository;

    @Autowired
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public TaskVO createTask(TaskVO taskVO) {

        ValidatorUtils.checkObjectIsNullOrThrowException(taskVO, INVALID_TASK_EXCEPTION_MESSAGE, InvalidTaskException.class);

        if (taskVO.getStatus() == Status.COMPLETED) {
            throw new InvalidTaskStatusException(INVALID_TASK_STATUS_EXCEPTION_MESSAGE);
        }
        TaskEntity taskEntity = TaskFactory.createTask(taskVO.getTitle(), taskVO.getDescription(), taskVO.getDueDate(), taskVO.getStatus(), taskVO.getPriority(), taskVO.getSubTasks());
        ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(taskEntity, INVALID_TASK_EXCEPTION_MESSAGE, FieldNotFoundException.class);
        repository.save(taskEntity);
        return BuilderMapper.parseObject(new TaskVO(), taskEntity);
    }

    @Override
    public TaskVO updateTask(TaskVO taskVO) {

        ValidatorUtils.checkObjectIsNullOrThrowException(taskVO, INVALID_TASK_EXCEPTION_MESSAGE, InvalidTaskException.class);
        TaskVO taskById = findTaskById(taskVO.getId());
        TaskEntity taskEntity = BuilderMapper.parseObject(new TaskEntity(), taskById);
        TaskEntity updatedTask = ValidatorUtils.updateFieldIfNotNull(taskEntity, taskVO, INVALID_TASK_EXCEPTION_MESSAGE, FieldNotFoundException.class);
        repository.save(updatedTask);
        return BuilderMapper.parseObject(new TaskVO(), updatedTask);
    }

    @Override
    public TaskVO updateTaskStatus(String id, UpdateStatusRequest updateStatusRequest) {

        TaskVO taskVO = findTaskById(id);

        if (updateStatusRequest.getStatus() == Status.COMPLETED) {
            checkSubtaskStatusIsCompleted(taskVO.getSubTasks());
        }
        taskVO.setStatus(updateStatusRequest.getStatus());
        TaskEntity taskEntity = ValidatorUtils.updateFieldIfNotNull(new TaskEntity(),
                taskVO, INVALID_TASK_EXCEPTION_MESSAGE, FieldNotFoundException.class);

        repository.save(taskEntity);
        return BuilderMapper.parseObject(new TaskVO(), taskEntity);

    }

    @Override
    public TaskVO findTaskById(String id) {

        TaskEntity taskEntity = repository.findById(id).orElseThrow(() -> new TaskNotFoundException(TASK_NOT_FOUND_EXCEPTION_MESSAGE));
        return BuilderMapper.parseObject(new TaskVO(), taskEntity);
    }

    @Override
    public Page<TaskVO> findAllTasks(Status status, Priority priority, LocalDateTime dueDate, Pageable pageable) {

        Specification<TaskEntity> taskEntitySpecification = TaskSpecificatios.withFilters(status, priority, dueDate);
        Page<TaskEntity> tasksByStatus = repository.findAll(taskEntitySpecification, pageable);
        List<TaskVO> taskVOS = tasksByStatus.getContent().stream().map(taskEntity -> BuilderMapper.parseObject(new TaskVO(), taskEntity)).toList();
        return new PageImpl<>(taskVOS, pageable, tasksByStatus.getTotalElements());
    }


    @Override
    public void deleteTaskById(String id) {

        TaskVO taskById = findTaskById(id);
        TaskEntity taskEntity = BuilderMapper.parseObject(new TaskEntity(), taskById);

        repository.delete(taskEntity);

    }

    private void checkSubtaskStatusIsCompleted(List<SubtaskEntity> subtaskEntities) {

        for (int i = 0; i < subtaskEntities.size(); i++) {
            if (subtaskEntities.get(i).getStatus() != Status.COMPLETED) {

                throw new SubTaskNotCompletedException(SUBTASK_NOT_COMPLETED_EXCEPTION_MESSAGE);

            }

        }


    }


}
