package com.victordev.learningDocker.service;

import com.victordev.learningDocker.model.Task;
import com.victordev.learningDocker.repository.TaskRepository;
import com.victordev.learningDocker.service.exception.TaskNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;

    private Task findById(Long id){
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public TaskService(TaskRepository repository){
        this.repository = repository;
    }

    public Task create(Task task) {
        return repository.save(task);
    }

    public List<Task> getAll() {
        return repository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        Task task = findById(id);
        repository.deleteById(task.getId());
    }

    public Task changePropertyDone(Long id) {
        Task task = findById(id);
        if (task.getDone()) {
            task.setDone(false);
        } else {
            task.setDone(true);
        }
        return repository.save(task);
    }
}
