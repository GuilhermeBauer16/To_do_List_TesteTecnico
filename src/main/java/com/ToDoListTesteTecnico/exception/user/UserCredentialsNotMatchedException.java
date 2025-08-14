package com.ToDoListTesteTecnico.exception.user;


import com.ToDoListTesteTecnico.Enum.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserCredentialsNotMatchedException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.INVALID_USER_CREDENTIALS_MESSAGE;

    public UserCredentialsNotMatchedException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
