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
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email: " + email
                        )
                );

        String roleName = "USER";

        if (user.getRole() != null &&
                user.getRole().getRoleName() != null &&
                !user.getRole().getRoleName().isBlank()) {

            roleName = user.getRole()
                    .getRoleName()
                    .trim()
                    .toUpperCase();
        }

        // Ensure exactly one ROLE_ prefix
        if (!roleName.startsWith("ROLE_")) {
            roleName = "ROLE_" + roleName;
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),

                // enabled
                user.isActive(),

                // accountNonExpired
                true,

                // credentialsNonExpired
                true,

                // accountNonLocked
                true,

                Collections.singletonList(
                        new SimpleGrantedAuthority(roleName)
                )
        );
    }
}