package com.tarpa.tourism.security.config;

import com.tarpa.tourism.exception.CustomAccessDeniedHandler;
import com.tarpa.tourism.exception.CustomAuthenticationEntryPoint;
import com.tarpa.tourism.security.jwt.JwtAuthenticationFilter;
import com.tarpa.tourism.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Apply CORS configuration first
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        // 1. Preflight requests permitted unconditionally
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 2. Public endpoints (permitted both with and without /api)
                        .requestMatchers(
                                "/api/health",
                                "/health",
                                "/api/auth/**",
                                "/auth/**",
                                "/api/feedbacks/testimonials",
                                "/api/bookings/*/invoice",
                                "/api/documents/download/**"
                        ).permitAll()

                        // 3. Role-restricted endpoints
                        .requestMatchers("/api/logistics/**").hasAnyRole("SUPER_ADMIN", "OPERATIONS_MANAGER")
                        .requestMatchers("/api/documents/**").hasAnyRole("SUPER_ADMIN", "OPERATIONS_MANAGER", "PERMITS_DOCUMENTATION_OFFICER")
                        .requestMatchers("/api/financials/**").hasAnyRole("SUPER_ADMIN", "MANAGING_DIRECTOR")
                        .requestMatchers("/api/permits/**").hasAnyRole("SUPER_ADMIN", "OPERATIONS_MANAGER", "PERMITS_DOCUMENTATION_OFFICER")

                        // 4. Everything else requires authentication
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Whitelist frontend origins explicitly
        configuration.setAllowedOriginPatterns(List.of(
                "https://satori-woad.vercel.app",
                "https://*.vercel.app",
                "http://localhost:5173",
                "http://localhost:3000",
                "http://localhost:4173"
        ));

        // Read FRONTEND_URL if provided on Render
        String allowedOriginsEnv = System.getenv("FRONTEND_URL");
        if (allowedOriginsEnv != null && !allowedOriginsEnv.isBlank()) {
            if ("*".equals(allowedOriginsEnv.trim())) {
                configuration.setAllowedOriginPatterns(List.of("*"));
            } else {
                List<String> origins = Arrays.stream(allowedOriginsEnv.split(","))
                        .map(String::trim)
                        .toList();
                configuration.setAllowedOriginPatterns(origins);
            }
        }

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "X-Requested-With", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}