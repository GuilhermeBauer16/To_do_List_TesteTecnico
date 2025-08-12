package com.ToDoListTesteTecnico.service;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.TaskEntity;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.exception.FieldNotFoundException;
import com.ToDoListTesteTecnico.exception.TaskNotFoundException;
import com.ToDoListTesteTecnico.factory.TaskFactory;
import com.ToDoListTesteTecnico.mapper.BuilderMapper;
import com.ToDoListTesteTecnico.repository.TaskRepository;
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

    private final TaskRepository repository;

    @Autowired
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public TaskVO createTask(TaskVO taskVO) {

        ValidatorUtils.checkObjectIsNullOrThrowException(taskVO, TASK_NOT_FOUND_EXCEPTION_MESSAGE, TaskNotFoundException.class);
        TaskEntity taskEntity = TaskFactory.createTask(taskVO.getTitle(), taskVO.getDescription(), taskVO.getDueDate(), taskVO.getStatus(), taskVO.getPriority(), taskVO.getSubTasks());
        ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(taskEntity, TASK_NOT_FOUND_EXCEPTION_MESSAGE, FieldNotFoundException.class);
        repository.save(taskEntity);
        return BuilderMapper.parseObject(new TaskVO(), taskEntity);
    }

    @Override
    public TaskVO updateTask(TaskVO taskVO) {
        return null;
    }

    @Override
    public TaskVO getTaskById(String id) {
        TaskEntity taskEntity = repository.findById(id).orElseThrow(() -> new TaskNotFoundException(TASK_NOT_FOUND_EXCEPTION_MESSAGE));
        return BuilderMapper.parseObject(new TaskVO(), taskEntity);
    }

    @Override
    public Page<TaskVO> findAllTasksByStatus(Status status, Priority priority, LocalDateTime dueDate, Pageable pageable) {

        Specification<TaskEntity> taskEntitySpecification = TaskSpecificatios.withFilters(status, priority, dueDate);
        Page<TaskEntity> tasksByStatus = repository.findAll(taskEntitySpecification, pageable);
        List<TaskVO> taskVOS = tasksByStatus.getContent().stream().map(taskEntity -> BuilderMapper.parseObject(new TaskVO(), taskEntity)).toList();
        return new PageImpl<>(taskVOS, pageable, tasksByStatus.getTotalElements());
    }


    @Override
    public void deleteTaskById(String id) {

        TaskEntity taskEntity = repository
                .findById(id).orElseThrow(() -> new TaskNotFoundException(TASK_NOT_FOUND_EXCEPTION_MESSAGE));

        repository.delete(taskEntity);

    }


}
