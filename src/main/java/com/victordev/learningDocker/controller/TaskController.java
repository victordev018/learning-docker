package com.victordev.learningDocker.controller;

import com.victordev.learningDocker.model.Task;
import com.victordev.learningDocker.model.User;
import com.victordev.learningDocker.model.dto.TaskCreatedResponseDTO;
import com.victordev.learningDocker.model.dto.TaskRequestDTO;
import com.victordev.learningDocker.service.TaskService;
import com.victordev.learningDocker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {


    private TaskService taskService;
    private UserService userService;

    public TaskController(TaskService taskService, UserService userService){
        this.taskService = taskService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<TaskCreatedResponseDTO> create(@RequestBody TaskRequestDTO requestDTO){
        User user = userService.findById(requestDTO.user_id());
        Task task = new Task(null, requestDTO.content(), false, user);
        task = taskService.create(task);
        TaskCreatedResponseDTO response = new TaskCreatedResponseDTO(task.getId());
        return ResponseEntity.status(201).body(response);
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
