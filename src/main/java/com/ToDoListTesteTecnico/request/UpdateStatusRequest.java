package com.ToDoListTesteTecnico.request;

import com.ToDoListTesteTecnico.Enum.Status;

public class UpdateStatusRequest {

    private Status status;

    public UpdateStatusRequest() {
    }

    public UpdateStatusRequest(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
