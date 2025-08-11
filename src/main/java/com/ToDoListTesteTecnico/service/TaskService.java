package com.ToDoListTesteTecnico.service;

import com.ToDoListTesteTecnico.entity.TaskEntity;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.factory.TaskFactory;
import com.ToDoListTesteTecnico.mapper.BuilderMapper;
import com.ToDoListTesteTecnico.repository.TaskRepository;
import com.ToDoListTesteTecnico.service.contract.TaskServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService implements TaskServiceContract {

    private final TaskRepository repository;

    @Autowired
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public TaskVO createTask(TaskVO task) {

        TaskEntity taskEntity = TaskFactory.createTask(task.getTitle(), task.getDescription(), task.getDueDate(), task.getStatus(), task.getPriority());
        repository.save(taskEntity);
        return BuilderMapper.parseObject(new TaskVO(), taskEntity);
    }

    @Override
    public TaskVO updateTask(TaskVO task) {
        return null;
    }

    @Override
    public TaskVO getTaskById(String id) {
        TaskEntity taskEntity = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));
        return BuilderMapper.parseObject(new TaskVO(), taskEntity);
    }

    @Override
    public void deleteTaskById(String id) {

    }
}
