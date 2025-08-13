package com.ToDoListTesteTecnico.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Priority {


    LOW("low"),


    MEDIUM("medium"),

    HIGH("high");

    private final String value;


    Priority(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Priority fromValue(String value) {
        if (value == null) return null;
        for (Priority p : Priority.values()) {
            if (p.value.equalsIgnoreCase(value)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Invalid Status: " + value);
    }
}
