package com.nazimov.tasklist.web.controller;

import com.nazimov.tasklist.domain.task.Task;
import com.nazimov.tasklist.domain.user.User;
import com.nazimov.tasklist.service.TaskService;
import com.nazimov.tasklist.service.UserService;
import com.nazimov.tasklist.web.dto.task.TaskDto;
import com.nazimov.tasklist.web.dto.user.UserDto;
import com.nazimov.tasklist.web.dto.validation.OnCreate;
import com.nazimov.tasklist.web.dto.validation.OnUpdate;
import com.nazimov.tasklist.web.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    private final TaskService taskService;

    private final Mapper<Task, TaskDto> taskMapper;

    private final Mapper<User, UserDto> userMapper;

    @PutMapping
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User savedUser = userService.update(user);
        return userMapper.toDto(savedUser);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("{id}/tasks")
    public List<TaskDto> getTasksByUserId(@PathVariable Long id) {
        List<Task> tasks = taskService.getAllByUserId(id);
        return taskMapper.toListDto(tasks);
    }

    @PostMapping("{id}/tasks")
    public TaskDto createTask(@PathVariable Long id,
                              @Validated(OnCreate.class) @RequestBody TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        Task createdTask = taskService.create(task, id);
        return taskMapper.toDto(createdTask);
    }

}
