package com.nazimov.tasklist.web.controller;

import com.nazimov.tasklist.domain.task.Task;
import com.nazimov.tasklist.service.TaskService;
import com.nazimov.tasklist.web.dto.task.TaskDto;
import com.nazimov.tasklist.web.dto.validation.OnUpdate;
import com.nazimov.tasklist.web.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final TaskService taskService;

    private final Mapper<Task, TaskDto> taskMapper;

    @PutMapping
    public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        Task savedTask = taskService.update(task);
        return taskMapper.toDto(savedTask);
    }

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        taskService.delete(id);
    }

}
