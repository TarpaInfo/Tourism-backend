package com.tarpa.tourism.service.impl;

import com.tarpa.tourism.entity.Role;
import com.tarpa.tourism.entity.User;
import com.tarpa.tourism.repository.RoleRepository;
import com.tarpa.tourism.repository.UserRepository;
import com.tarpa.tourism.request.LoginRequest;
import com.tarpa.tourism.request.RegisterRequest;
import com.tarpa.tourism.response.ApiResponse;
import com.tarpa.tourism.response.LoginResponse;
import com.tarpa.tourism.security.jwt.JwtService;
import com.tarpa.tourism.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ApiResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return new ApiResponse(false, "Email already exists.");
        }

        Role role = roleRepository.findByRoleName("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .role(role)
                .build();

        userRepository.save(user);

        return new ApiResponse(true, "User registered successfully.");
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDetails userDetails =
                org.springframework.security.core.userdetails.User
                        .builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getRole().getRoleName())
                        .build();

        String token = jwtService.generateToken(userDetails);

        return new LoginResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole().getRoleName(),
                token,
                "Login successful."
        );
    }

}