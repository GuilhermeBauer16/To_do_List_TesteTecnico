package com.ToDoListTesteTecnico.entity.values;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.SubtaskEntity;
import com.ToDoListTesteTecnico.entity.TaskEntity;
import com.ToDoListTesteTecnico.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class TaskVO {

    private String id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Status status;
    private Priority priority;
    private List<SubtaskEntity> subTasks = new ArrayList<>();
    private UserEntity user;

    public TaskVO() {
    }

    public TaskVO(String id, String title, String description, LocalDateTime dueDate, Status status, Priority priority, List<SubtaskEntity> subTasks) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
        this.subTasks = subTasks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public List<SubtaskEntity> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<SubtaskEntity> subTasks) {
        this.subTasks = subTasks;
    }
}
