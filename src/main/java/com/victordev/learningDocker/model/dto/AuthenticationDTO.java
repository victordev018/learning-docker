package com.victordev.learningDocker.model.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(String username,String password) {
}
