package com.tarpa.tourism.security.config;

import com.tarpa.tourism.entity.Role;
import com.tarpa.tourism.entity.User;
import com.tarpa.tourism.repository.RoleRepository;
import com.tarpa.tourism.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String email = "sagarnepal98@gmail.com";

        if (userRepository.findByEmail(email).isEmpty()) {
            // Find existing SUPER_ADMIN role or create it
            Role adminRole = roleRepository.findAll().stream()
                    .filter(r -> r.getRoleName() != null &&
                            (r.getRoleName().equalsIgnoreCase("ROLE_SUPER_ADMIN") ||
                                    r.getRoleName().equalsIgnoreCase("SUPER_ADMIN")))
                    .findFirst()
                    .orElseGet(() -> roleRepository.save(
                            Role.builder()
                                    .roleName("ROLE_SUPER_ADMIN")
                                    .description("Super Administrator with full operations access")
                                    .build()
                    ));

            // Create and persist the user matching User entity fields
            User user = User.builder()
                    .firstName("Sagar")
                    .lastName("Nepal")
                    .email(email)
                    .password(passwordEncoder.encode("Sagar123"))
                    .phone("+9779801046037")
                    .active(true)
                    .role(adminRole)
                    .build();

            userRepository.save(user);
            log.info(">>> Seeded SUPER_ADMIN user: {}", email);
        }
    }
}