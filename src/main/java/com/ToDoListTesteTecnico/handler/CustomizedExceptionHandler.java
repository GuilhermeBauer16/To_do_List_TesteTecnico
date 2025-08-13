package com.ToDoListTesteTecnico.handler;

import com.ToDoListTesteTecnico.exception.EmailAllReadyRegisterException;
import com.ToDoListTesteTecnico.exception.ExceptionResponse;
import com.ToDoListTesteTecnico.exception.UserNotFoundException;
import com.ToDoListTesteTecnico.exception.subtask.InvalidSubTaskException;
import com.ToDoListTesteTecnico.exception.subtask.InvalidSubTaskStatusException;
import com.ToDoListTesteTecnico.exception.subtask.SubTaskNotCompletedException;
import com.ToDoListTesteTecnico.exception.subtask.SubTaskNotFoundException;
import com.ToDoListTesteTecnico.exception.task.InvalidTaskException;
import com.ToDoListTesteTecnico.exception.task.InvalidTaskStatusException;
import com.ToDoListTesteTecnico.exception.task.TaskNotFoundException;
import com.ToDoListTesteTecnico.exception.utils.FieldNotFoundException;
import com.ToDoListTesteTecnico.exception.utils.UnknownPriorityValueException;
import com.ToDoListTesteTecnico.exception.utils.UnknownStatusValueException;
import com.ToDoListTesteTecnico.exception.utils.ValidationUtilsException;
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
            TaskNotFoundException.class,
            SubTaskNotFoundException.class,
            UserNotFoundException.class,}
    )
    public final ResponseEntity<ExceptionResponse> handleWithNotFoundException(Exception exception, WebRequest webRequest) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage()
                , webRequest.getDescription(false),
                new Date());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ValidationUtilsException.class,
            SubTaskNotCompletedException.class,
            InvalidSubTaskStatusException.class,
            InvalidTaskStatusException.class,
            InvalidTaskException.class,
            InvalidSubTaskStatusException.class,
            InvalidSubTaskException.class,
            UnknownPriorityValueException.class,
            UnknownStatusValueException.class,
            EmailAllReadyRegisterException.class
    })
    public final ResponseEntity<ExceptionResponse> handleWithBadRequestException(Exception exception, WebRequest webRequest) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage()
                , webRequest.getDescription(false),
                new Date());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
