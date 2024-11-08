package com.victordev.learningDocker.controller;

import com.victordev.learningDocker.model.Task;
import com.victordev.learningDocker.model.User;
import com.victordev.learningDocker.model.dto.UserCreatedResponseDTO;
import com.victordev.learningDocker.model.dto.UserRequestDTO;
import com.victordev.learningDocker.model.dto.UserResponseDTO;
import com.victordev.learningDocker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserCreatedResponseDTO> create(@RequestBody UserRequestDTO userRequestDTO){
        User user = userService.create(new User(userRequestDTO));
        UserCreatedResponseDTO response = new UserCreatedResponseDTO(user.getId());
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
        User user = userService.findById(id);
        UserResponseDTO response = new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<Task>> getTasks(@PathVariable Long id){
        List<Task> tasks = userService.getTasksFromUser(id);
        return ResponseEntity.status(200).body(tasks);
    }

}
