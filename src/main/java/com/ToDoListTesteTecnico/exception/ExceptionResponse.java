package com.ToDoListTesteTecnico.exception;

import java.util.Date;

public record ExceptionResponse(String message, String details, Date timestamp) {
}
