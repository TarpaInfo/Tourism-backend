package com.tarpa.tourism.email.service.impl;

import com.tarpa.tourism.email.service.EmailService;
import com.tarpa.tourism.util.EmailTemplateBuilder;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }

    @Override
    public void sendEmailWithCc(String to, String cc, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setCc(cc);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }

    @Async
    @Override
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        if (to == null || to.isBlank()) {
            log.warn("Skipping email dispatch: recipient address is empty.");
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("info@rabinepal.com.np");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            mailSender.send(message);
            log.info("HTML email successfully sent to: {}", to);
            // #region agent log
            try {
                String line = String.format(
                        "{\"sessionId\":\"c32bd2\",\"hypothesisId\":\"D\",\"location\":\"EmailServiceImpl.java:sendHtmlEmail\",\"message\":\"html email sent\",\"data\":{\"to\":\"%s\",\"asyncEnabledGuess\":false,\"thread\":\"%s\"},\"timestamp\":%d}%n",
                        to,
                        Thread.currentThread().getName(),
                        System.currentTimeMillis());
                java.nio.file.Files.writeString(
                        java.nio.file.Path.of("C:\\Users\\NITRO V15\\Desktop\\Tourism Managment System\\tourism-management-system\\debug-c32bd2.log"),
                        line,
                        java.nio.file.StandardOpenOption.CREATE,
                        java.nio.file.StandardOpenOption.APPEND);
            } catch (Exception ignored) {}
            // #endregion
        } catch (MessagingException e) {
            // #region agent log
            try {
                String line = String.format(
                        "{\"sessionId\":\"c32bd2\",\"hypothesisId\":\"D\",\"location\":\"EmailServiceImpl.java:sendHtmlEmail\",\"message\":\"html email failed\",\"data\":{\"to\":\"%s\",\"exMessage\":\"%s\",\"thread\":\"%s\"},\"timestamp\":%d}%n",
                        to,
                        String.valueOf(e.getMessage()).replace("\"", "'"),
                        Thread.currentThread().getName(),
                        System.currentTimeMillis());
                java.nio.file.Files.writeString(
                        java.nio.file.Path.of("C:\\Users\\NITRO V15\\Desktop\\Tourism Managment System\\tourism-management-system\\debug-c32bd2.log"),
                        line,
                        java.nio.file.StandardOpenOption.CREATE,
                        java.nio.file.StandardOpenOption.APPEND);
            } catch (Exception ignored) {}
            // #endregion
            log.error("Failed to send HTML email to {}: {}", to, e.getMessage());
        }
    }

    @Async
    @Override
    public void sendHtmlEmailWithCc(String to, String cc, String subject, String title, String bodyMessage) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            if (cc != null && !cc.trim().isEmpty()) {
                helper.setCc(cc);
            }
            helper.setSubject(subject);

            String htmlContent = EmailTemplateBuilder.buildHtmlTemplate(title, bodyMessage);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            log.info("HTML Email sent to: {} (CC: {})", to, cc);
        } catch (Exception e) {
            log.error("Failed to send HTML email with CC: {}", e.getMessage());
        }
    }

    @Async
    @Override
    public void sendHtmlEmailWithAttachment(String to, String subject, String title, String bodyMessage, byte[] attachmentData, String attachmentName) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);

            String htmlContent = EmailTemplateBuilder.buildHtmlTemplate(title, bodyMessage);
            helper.setText(htmlContent, true);

            if (attachmentData != null && attachmentData.length > 0) {
                ByteArrayResource pdfAttachment = new ByteArrayResource(attachmentData);
                helper.addAttachment(attachmentName, pdfAttachment);
            }

            mailSender.send(mimeMessage);
            log.info("HTML Email with attachment sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send HTML email with attachment: {}", e.getMessage());
        }
    }

    @Async
    @Override
    public void sendHtmlEmailWithCcAndAttachment(String to, String cc, String subject, String title, String bodyMessage, byte[] attachmentData, String attachmentName) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            if (cc != null && !cc.trim().isEmpty()) {
                helper.setCc(cc);
            }
            helper.setSubject(subject);

            String htmlContent = EmailTemplateBuilder.buildHtmlTemplate(title, bodyMessage);
            helper.setText(htmlContent, true);

            if (attachmentData != null && attachmentData.length > 0) {
                ByteArrayResource pdfAttachment = new ByteArrayResource(attachmentData);
                helper.addAttachment(attachmentName, pdfAttachment);
            }

            mailSender.send(mimeMessage);
            log.info("HTML Email with attachment sent to: {} (CC: {})", to, cc);
        } catch (Exception e) {
            log.error("Failed to send HTML email with attachment (CC): {}", e.getMessage());
        }
    }
}