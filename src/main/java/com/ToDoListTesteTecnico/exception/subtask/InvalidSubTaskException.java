package com.ToDoListTesteTecnico.exception.subtask;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidSubTaskException extends RuntimeException {

    public InvalidSubTaskException(String message) {
        super(message);
    }
}
