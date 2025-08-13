package com.ToDoListTesteTecnico.exception.subtask;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidSubTaskStatusException extends RuntimeException {

    public InvalidSubTaskStatusException(String message) {
        super(message);
    }
}
