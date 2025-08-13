package com.ToDoListTesteTecnico.exception.subtask;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubTaskNotFoundException extends RuntimeException {

    public SubTaskNotFoundException(String message) {
        super(message);
    }
}
