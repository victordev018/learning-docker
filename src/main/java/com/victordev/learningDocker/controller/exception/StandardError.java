package com.victordev.learningDocker.controller.exception;

public record StandardError(Integer status, String error) {
}
