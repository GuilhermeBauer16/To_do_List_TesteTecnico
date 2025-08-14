package com.ToDoListTesteTecnico.Enum;

import org.springframework.http.HttpStatus;


public enum ExceptionDetails {



    FIELD_NOT_FOUND_MESSAGE("The field %s can not be null or empty!", HttpStatus.NOT_FOUND),
    UNKNOWN_PRIORITY_MESSAGE("Unknown priority with the name %s", HttpStatus.BAD_REQUEST),
    UNKNOWN_STATUS_MESSAGE("Unknown status with the name %s", HttpStatus.BAD_REQUEST),
    EXCEPTION_TYPE_NOT_THROWN("Can not thrown the exception of the type: %s", HttpStatus.NOT_FOUND),
    INVALID_USER_CREDENTIALS_MESSAGE("Occur an error to registry the user for the reason: %s", HttpStatus.FORBIDDEN),
    EMAIL_ALREADY_REGISTER_MESSAGE("Occur an error into the registration of the email in reason of: %s", HttpStatus.BAD_REQUEST);





    private final String message;
    private final HttpStatus httpStatus;

    ExceptionDetails(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String formatErrorMessage(String message) {
        return String.format(this.message, message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
