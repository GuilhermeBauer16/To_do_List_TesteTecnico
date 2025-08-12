package com.ToDoListTesteTecnico.factory;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.TaskEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public class TaskFactory {

    public TaskFactory() {
    }

    public static TaskEntity createTask(String title, String description, LocalDateTime dueDate, Status status, Priority priority, List<TaskEntity> subTasks) {
        return new TaskEntity(UUID.randomUUID().toString(), title, description, dueDate, status, priority,subTasks);
    }


}


