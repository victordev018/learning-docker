package com.victordev.learningDocker.controller;

import com.victordev.learningDocker.model.Task;
import com.victordev.learningDocker.model.User;
import com.victordev.learningDocker.model.dto.TaskRequestDTO;
import com.victordev.learningDocker.service.TaskService;
import com.victordev.learningDocker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {


    private final TaskService taskService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody TaskRequestDTO requestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuthenticated = (User) authentication.getPrincipal();
        Task task = new Task(null, requestDTO.content(), false, userAuthenticated);
        taskService.create(task);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long taskId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuthenticated = (User) authentication.getPrincipal();

        if(!userService.taskBelongsToTheUser(userAuthenticated, taskId)) throw new AccessDeniedException("User no authorized");

        taskService.deleteById(taskId);
        return ResponseEntity.status(200).build();
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<Void> changePropertyDone(@PathVariable Long taskId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuthenticated = (User) authentication.getPrincipal();

        if(!userService.taskBelongsToTheUser(userAuthenticated, taskId)) throw new AccessDeniedException("User no authorized");

        Task task = taskService.changePropertyDone(taskId);
        return ResponseEntity.status(200).build();
    }

}
