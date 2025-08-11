package com.ToDoListTesteTecnico.Enum;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {

    @JsonProperty("pending")
    PENDING("pending"),

    @JsonProperty("in_progress")
    IN_PROGRESS("in_progress"),

    @JsonProperty("completed")
    COMPLETED("completed");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
