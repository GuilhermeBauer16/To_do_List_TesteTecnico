package com.ToDoListTesteTecnico.exception;


import com.ToDoListTesteTecnico.Enum.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailAllReadyRegisterException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.EMAIL_ALREADY_REGISTER_MESSAGE;

    public EmailAllReadyRegisterException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
