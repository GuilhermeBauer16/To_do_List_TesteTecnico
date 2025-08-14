package com.ToDoListTesteTecnico.response;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskResponse {

    private String id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Status status;
    private Priority priority;
    private List<SubtaskResponse> subTasks = new ArrayList<>();

    public TaskResponse() {
    }

    public TaskResponse(String id, String title, String description, LocalDateTime dueDate, Status status, Priority priority, List<SubtaskResponse> subTasks) {
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

    public List<SubtaskResponse> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<SubtaskResponse> subTasks) {
        this.subTasks = subTasks;
    }
}
