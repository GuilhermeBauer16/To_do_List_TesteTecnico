package com.ToDoListTesteTecnico.exception.utils;

import com.ToDoListTesteTecnico.Enum.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnknownPriorityValueException extends RuntimeException {


    public static final ExceptionDetails ERROR = ExceptionDetails.UNKNOWN_PRIORITY_MESSAGE;

    public UnknownPriorityValueException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
