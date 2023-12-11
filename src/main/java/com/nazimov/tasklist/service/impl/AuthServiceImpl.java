package com.nazimov.tasklist.service.impl;

import com.nazimov.tasklist.domain.user.User;
import com.nazimov.tasklist.service.AuthService;
import com.nazimov.tasklist.service.UserService;
import com.nazimov.tasklist.web.dto.auth.JwtRequest;
import com.nazimov.tasklist.web.dto.auth.JwtResponse;
import com.nazimov.tasklist.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;


    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword())
        );  //authenticate() - вызывается имплементация UserDetailsService - JwtUserDetailsService, метод loadUserByUsername()
        //где происходит получение пользователя из БД, конвертация в UserDetails
        //затем его пароль из БД сравнивается с паролем из loginRequest с использованием BCryptPasswordEncoder
        //если пароли не совпали, то будет выброшено исключение
        User user = userService.getByUsername(loginRequest.getUsername());
        jwtResponse.setId(user.getId());
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(tokenProvider.createAccessToken(user.getId(), user.getUsername(), user.getRoles()));
        jwtResponse.setRefreshToken(tokenProvider.createRefreshToken(user.getId(), user.getUsername()));
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return tokenProvider.refreshUserTokens(refreshToken);
    }

}
