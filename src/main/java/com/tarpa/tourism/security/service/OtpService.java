package com.tarpa.tourism.security.service;

import com.tarpa.tourism.security.model.OtpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    // Temporary storage in server memory
    private final Map<String, OtpSession> sessionStore = new ConcurrentHashMap<>();

    // 1. Create 6-digit code and send it
    public OtpSession createAndSendOtp(String email, String phone) {
        // Generate random 6-digit number
        String code = String.format("%06d", new SecureRandom().nextInt(1000000));
        String sessionToken = UUID.randomUUID().toString();

        OtpSession session = OtpSession.builder()
                .sessionToken(sessionToken)
                .email(email)
                .phoneNumber(phone)
                .otpHash(code)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();

        sessionStore.put(sessionToken, session);

        // Send the code to user's email
        sendEmail(email, code);

        // (SMS gateway will plug in here later)
        log.info("OTP generated for {}: {}", email, code);

        return session;
    }

    // 2. Check if user typed the right code
    public boolean verifyOtp(String sessionToken, String enteredCode) {
        OtpSession session = sessionStore.get(sessionToken);

        if (session == null) {
            throw new RuntimeException("Session expired or not found. Please log in again.");
        }

        if (session.getExpiresAt().isBefore(LocalDateTime.now())) {
            sessionStore.remove(sessionToken);
            throw new RuntimeException("OTP code has expired. Please request a new one.");
        }

        if (!session.getOtpHash().equals(enteredCode.trim())) {
            throw new RuntimeException("Invalid verification code.");
        }

        // Code matched! Clean up so it can't be used twice
        sessionStore.remove(sessionToken);
        return true;
    }

    public OtpSession getSession(String sessionToken) {
        return sessionStore.get(sessionToken);
    }

    @Async
    public void sendEmail(String toEmail, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail);
            message.setTo(toEmail);
            message.setSubject("Tarpa Logistics: Your Login Verification Code");
            message.setText("Your one-time login verification code is: " + otp + "\n\nThis code expires in 5 minutes.");
            mailSender.send(message);
        } catch (Exception ex) {
            log.error("Failed to email OTP to {}: {}", toEmail, ex.getMessage());
        }
    }
}