package com.tarpa.tourism.email.service;

public interface EmailService {

    void sendEmail(
            String to,
            String subject,
            String message
    );

    void sendEmailWithCc(
            String to,
            String cc,
            String subject,
            String message
    );

    // --- NEW HTML EMAIL METHODS ---

    void sendHtmlEmail(String to, String subject, String htmlBody);

    void sendHtmlEmailWithCc(
            String to,
            String cc,
            String subject,
            String title,
            String bodyMessage
    );

    void sendHtmlEmailWithAttachment(
            String to,
            String subject,
            String title,
            String bodyMessage,
            byte[] attachmentData,
            String attachmentName
    );

    void sendHtmlEmailWithCcAndAttachment(
            String to,
            String cc,
            String subject,
            String title,
            String bodyMessage,
            byte[] attachmentData,
            String attachmentName
    );
}