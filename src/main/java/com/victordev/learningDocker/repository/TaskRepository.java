package com.victordev.learningDocker.repository;

import com.victordev.learningDocker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUserIdOrderById(Long user_id);
}
