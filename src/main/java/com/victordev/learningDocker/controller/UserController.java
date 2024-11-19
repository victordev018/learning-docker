package com.victordev.learningDocker.controller;

import com.victordev.learningDocker.model.Task;
import com.victordev.learningDocker.model.User;
import com.victordev.learningDocker.model.dto.TaskResponseDTO;
import com.victordev.learningDocker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponseDTO>> getTasks(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuthenticated = (User) authentication.getPrincipal();
        List<TaskResponseDTO> tasks = userService.getTasksFromUser(userAuthenticated.getId());
        return ResponseEntity.status(200).body(tasks);
    }

}
