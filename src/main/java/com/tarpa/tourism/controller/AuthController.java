package com.tarpa.tourism.controller;

import com.tarpa.tourism.request.LoginRequest;
import com.tarpa.tourism.request.RegisterRequest;
import com.tarpa.tourism.response.ApiResponse;
import com.tarpa.tourism.response.LoginResponse;
import com.tarpa.tourism.service.AuthService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    // ==========================
    // REGISTER
    // ==========================

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        ApiResponse response =
                authService.register(request);

        return ResponseEntity.ok(response);
    }


    // ==========================
    // LOGIN
    // ==========================

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response =
                authService.login(request);

        return ResponseEntity.ok(response);
    }

}