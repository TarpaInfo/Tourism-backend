package com.tarpa.tourism.email.controller;

import com.tarpa.tourism.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailTestController {

    private final EmailService emailService;

    @PostMapping("/test")
    public ResponseEntity<String> sendTestEmail(
            @RequestParam String to) {

        emailService.sendEmail(
                to,
                "Tourism Management System - Test Email",
                "Hello!\n\n"
                        + "This is a test email from the "
                        + "Tourism Management System.\n\n"
                        + "Email integration is working successfully!"
        );

        return ResponseEntity.ok(
                "Test email sent successfully to: " + to
        );
    }
}