package com.tarpa.tourism.security.service;

import com.tarpa.tourism.entity.User;
import com.tarpa.tourism.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Safely extract role name
        String roleName = (user.getRole() != null && user.getRole().getRoleName() != null)
                ? user.getRole().getRoleName().trim()
                : "USER";

        // Guarantee exactly ONE "ROLE_" prefix
        String authorityString = roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName;

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isActive(),
                true,
                true,
                true,
                Collections.singletonList(new SimpleGrantedAuthority(authorityString))
        );
    }
}