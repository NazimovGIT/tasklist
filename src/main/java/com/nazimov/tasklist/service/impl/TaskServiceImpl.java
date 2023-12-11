package com.nazimov.tasklist.service.impl;

import com.nazimov.tasklist.domain.exception.ResourceNotFoundException;
import com.nazimov.tasklist.domain.task.Status;
import com.nazimov.tasklist.domain.task.Task;
import com.nazimov.tasklist.domain.user.User;
import com.nazimov.tasklist.repository.TaskRepository;
import com.nazimov.tasklist.service.TaskService;
import com.nazimov.tasklist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Task getById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllByUserId(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public Task create(Task task, Long userId) {
        if (task.getStatus() != null) {
            task.setStatus(Status.TODO);
        }
        User user = userService.getById(userId);
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task update(Task task) {
        Task existing = getById(task.getId());
        if (task.getStatus() == null) {
            existing.setStatus(Status.TODO);
        } else {
            existing.setStatus(task.getStatus());
        }
        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        existing.setStatus(task.getStatus());
        existing.setExpirationDate(task.getExpirationDate());
        return taskRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

}
