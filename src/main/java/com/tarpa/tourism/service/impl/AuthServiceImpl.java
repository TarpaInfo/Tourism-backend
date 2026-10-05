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
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
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
                .orElseGet(() -> roleRepository.save(
                        Role.builder()
                                .roleName("CUSTOMER")
                                .description("Standard Customer")
                                .build()
                ));

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
        // 1. Authenticate user credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        // 2. Fetch User entity
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Format role name safely
        String rawRoleName = (user.getRole() != null && user.getRole().getRoleName() != null)
                ? user.getRole().getRoleName()
                : "CUSTOMER";

        String roleWithPrefix = rawRoleName.startsWith("ROLE_") ? rawRoleName : "ROLE_" + rawRoleName;

        // 4. Build UserDetails using authorities (avoids Spring's illegal argument on .roles())
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(roleWithPrefix))
        );

        // 5. Generate token
        String token = jwtService.generateToken(userDetails);

        return new LoginResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                rawRoleName,
                token,
                "Login successful."
        );
    }
}