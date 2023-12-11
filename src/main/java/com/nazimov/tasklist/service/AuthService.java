package com.nazimov.tasklist.service;

import com.nazimov.tasklist.web.dto.auth.JwtRequest;
import com.nazimov.tasklist.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);

}
