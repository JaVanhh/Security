package com.demo.secutity.service;

import com.demo.secutity.dto.LoginRequest;
import com.demo.secutity.dto.LoginResponse;
import com.demo.secutity.dto.RegisterRequest;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    LoginResponse registerUser(RegisterRequest request);
}
