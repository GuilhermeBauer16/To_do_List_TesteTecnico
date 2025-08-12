package com.ToDoListTesteTecnico.handler;

import com.ToDoListTesteTecnico.exception.ExceptionResponse;
import com.ToDoListTesteTecnico.exception.FieldNotFoundException;
import com.ToDoListTesteTecnico.exception.TaskNotFoundException;
import com.ToDoListTesteTecnico.exception.ValidationUtilsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler {

    @ExceptionHandler({
            FieldNotFoundException.class,
            TaskNotFoundException.class}
    )
    public final ResponseEntity<ExceptionResponse> handleWithNotFoundException(Exception exception, WebRequest webRequest) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage()
                , webRequest.getDescription(false),
                new Date());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationUtilsException.class)
    public final ResponseEntity<ExceptionResponse> handleWithBadRequestException(Exception exception, WebRequest webRequest) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage()
                , webRequest.getDescription(false),
                new Date());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
