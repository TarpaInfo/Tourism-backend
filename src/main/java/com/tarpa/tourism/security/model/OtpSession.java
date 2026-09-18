package com.tarpa.tourism.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpSession {
    private String sessionToken;
    private String email;
    private String phoneNumber;
    private String otpHash;
    private LocalDateTime expiresAt;
    private int attempts;
}