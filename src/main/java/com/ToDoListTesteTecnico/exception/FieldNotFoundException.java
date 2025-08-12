package com.ToDoListTesteTecnico.exception;

import com.ToDoListTesteTecnico.Enum.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FieldNotFoundException extends RuntimeException {


    public static final ExceptionDetails ERROR = ExceptionDetails.FIELD_NOT_FOUND_MESSAGE;

    public FieldNotFoundException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
