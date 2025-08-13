package com.ToDoListTesteTecnico.factory;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.SubtaskEntity;
import com.ToDoListTesteTecnico.entity.TaskEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public class SubtaskFactory {

    public SubtaskFactory() {
    }

    public static SubtaskEntity createSubtask(String title, String description, LocalDateTime dueDate, Status status, Priority priority) {
        return new SubtaskEntity(UUID.randomUUID().toString(), title, description, dueDate, status, priority);
    }


}


