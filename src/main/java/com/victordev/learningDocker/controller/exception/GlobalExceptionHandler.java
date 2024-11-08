package com.victordev.learningDocker.controller.exception;
import com.victordev.learningDocker.service.exception.TaskNotFoundException;
import com.victordev.learningDocker.service.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StandardError> handleUserNotFound(UserNotFoundException exception){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError response = new StandardError(status.value(), exception.getMessage());
        return ResponseEntity.status(status.value()).body(response);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<StandardError> handleTaskNotFound(TaskNotFoundException exception){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError response = new StandardError(status.value(), exception.getMessage());
        return ResponseEntity.status(status.value()).body(response);
    }
}
