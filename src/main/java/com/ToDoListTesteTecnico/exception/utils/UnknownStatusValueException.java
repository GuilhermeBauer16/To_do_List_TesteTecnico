package com.ToDoListTesteTecnico.exception.utils;

import com.ToDoListTesteTecnico.Enum.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnknownStatusValueException extends RuntimeException {


    public static final ExceptionDetails ERROR = ExceptionDetails.UNKNOWN_STATUS_MESSAGE;

    public UnknownStatusValueException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
