package com.ToDoListTesteTecnico.exception.subtask;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SubTaskNotCompletedException extends RuntimeException {

    public SubTaskNotCompletedException(String message) {
        super(message);
    }
}
