package com.ToDoListTesteTecnico.factory;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.SubtaskEntity;
import com.ToDoListTesteTecnico.entity.TaskEntity;
import com.ToDoListTesteTecnico.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public class TaskFactory {

    public TaskFactory() {
    }

    public static TaskEntity createTask(String title, String description, LocalDateTime dueDate, Status status, Priority priority, List<SubtaskEntity> subTasks, UserEntity user) {
        return new TaskEntity(UUID.randomUUID().toString(), title, description, dueDate, status, priority,subTasks,user);
    }


}


