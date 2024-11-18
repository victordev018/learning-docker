package com.victordev.learningDocker.service;

import com.victordev.learningDocker.model.Task;
import com.victordev.learningDocker.model.User;
import com.victordev.learningDocker.service.exception.UserNotFoundException;
import com.victordev.learningDocker.repository.TaskRepository;
import com.victordev.learningDocker.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private TaskRepository taskRepository;

    public UserService(UserRepository userRepository, TaskRepository taskRepository){
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public User findById(Long id) {
        Optional<User> promise = userRepository.findById(id);
        return promise.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public List<Task> getTasksFromUser(Long id) {
        findById(id);
        return taskRepository.findAllByUserId(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
