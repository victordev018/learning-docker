package com.victordev.learningDocker.service.exception;

public class TaskNotFoundException extends RuntimeException{

    private static final Long serialVersionUUID = 1L;

    public TaskNotFoundException(String message){
        super(message);
    }
}
