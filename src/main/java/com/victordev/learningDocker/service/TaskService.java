package com.victordev.learningDocker.service;

import com.victordev.learningDocker.model.Task;
import com.victordev.learningDocker.repository.TaskRepository;
import com.victordev.learningDocker.service.exception.TaskNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private TaskRepository repository;

    public TaskService(TaskRepository repository){
        this.repository = repository;
    }

    public Task create(Task task) {
        return repository.save(task);
    }

    public List<Task> getAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        Task task = repository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        repository.delete(task);
    }
}
