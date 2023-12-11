package com.nazimov.tasklist.web.controller;

import com.nazimov.tasklist.domain.user.User;
import com.nazimov.tasklist.service.AuthService;
import com.nazimov.tasklist.service.UserService;
import com.nazimov.tasklist.web.dto.auth.JwtRequest;
import com.nazimov.tasklist.web.dto.auth.JwtResponse;
import com.nazimov.tasklist.web.dto.user.UserDto;
import com.nazimov.tasklist.web.dto.validation.OnCreate;
import com.nazimov.tasklist.web.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final Mapper<User, UserDto> userMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDto register(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.create(user);
        return userMapper.toDto(createdUser);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }

}
