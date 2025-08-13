package com.ToDoListTesteTecnico.request;

public class SubTaskRequest {

    private String taskId;
    private String subTaskId;

    public SubTaskRequest() {
    }

    public SubTaskRequest(String taskId, String subTaskId) {
        this.taskId = taskId;
        this.subTaskId = subTaskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(String subTaskId) {
        this.subTaskId = subTaskId;
    }
}
