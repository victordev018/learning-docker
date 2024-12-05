package com.victordev.learningDocker.service;

import com.victordev.learningDocker.model.Task;
import com.victordev.learningDocker.model.User;
import com.victordev.learningDocker.model.dto.TaskRequestDTO;
import com.victordev.learningDocker.model.dto.TaskResponseDTO;
import com.victordev.learningDocker.service.exception.UserNotFoundException;
import com.victordev.learningDocker.repository.TaskRepository;
import com.victordev.learningDocker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    public User findById(Long id) {
        Optional<User> promise = userRepository.findById(id);
        return promise.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public List<TaskResponseDTO> getTasksFromUser(Long id) {
        findById(id);
        List<TaskResponseDTO> taskResponseDTOS = taskRepository.findAllByUserIdOrderById(id).stream()
                .map( item -> new TaskResponseDTO(item.getId(), item.getContent(), item.getDone()))
                .toList();
        return taskResponseDTOS;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean taskBelongsToTheUser(User userAuthenticated, Long taskId) {
        Task task = taskService.findById(taskId);
        return userAuthenticated.getId().equals(task.getUser().getId());
    }
}
