package com.ToDoListTesteTecnico.Enum;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Priority {

    @JsonProperty("low")
    LOW("low"),

    @JsonProperty("medium")
    MEDIUM("medium"),

    @JsonProperty("high")
    HIGH("high");

    private String value;

    Priority(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
