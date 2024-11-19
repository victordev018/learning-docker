package com.victordev.learningDocker.controller;

import com.victordev.learningDocker.model.Task;
import com.victordev.learningDocker.model.User;
import com.victordev.learningDocker.model.dto.TaskCreatedResponseDTO;
import com.victordev.learningDocker.model.dto.TaskRequestDTO;
import com.victordev.learningDocker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {


    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody TaskRequestDTO requestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuthenticated = (User) authentication.getPrincipal();
        Task task = new Task(null, requestDTO.content(), false, userAuthenticated);
        taskService.create(task);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        taskService.deleteById(id);
        return ResponseEntity.status(200).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskCreatedResponseDTO> changePropertyDone(@PathVariable Long id){
        Task task = taskService.changePropertyDone(id);
        TaskCreatedResponseDTO response = new TaskCreatedResponseDTO(task.getId());
        return ResponseEntity.status(200).body(response);
    }

}
