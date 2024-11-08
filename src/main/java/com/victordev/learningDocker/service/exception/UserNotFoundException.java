package com.victordev.learningDocker.service.exception;

public class UserNotFoundException extends RuntimeException{

    private static final Long serialVersionUUID = 1L;

    public UserNotFoundException(String message){
        super(message);
    }

}
