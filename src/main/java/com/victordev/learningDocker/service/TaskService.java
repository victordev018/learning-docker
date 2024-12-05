package com.victordev.learningDocker.service;

import com.victordev.learningDocker.model.Task;
import com.victordev.learningDocker.repository.TaskRepository;
import com.victordev.learningDocker.service.exception.TaskNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    public Task findById(Long id){
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public Task create(Task task) {
        return repository.save(task);
    }

    @Transactional
    public void deleteById(Long id) {
        Task task = findById(id);
        repository.deleteById(task.getId());
    }

    @Transactional
    public Task changePropertyDone(Long id) {
        Task task = findById(id);
        if (task.getDone()) {
            task.setDone(false);
        } else {
            task.setDone(true);
        }
        return repository.save(task);
    }

    @Transactional
    public void update(Long taskId, String content) {
        Task task = findById(taskId);
        task.setContent(content);
        repository.save(task);
    }
}
