package com.tarpa.tourism.service;

import com.tarpa.tourism.request.LoginRequest;
import com.tarpa.tourism.request.RegisterRequest;
import com.tarpa.tourism.response.ApiResponse;
import com.tarpa.tourism.response.LoginResponse;

public interface AuthService {

    ApiResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}