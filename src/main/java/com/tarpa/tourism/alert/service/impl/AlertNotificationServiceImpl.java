package com.tarpa.tourism.alert.service.impl;

import com.tarpa.tourism.alert.entity.Alert;
import com.tarpa.tourism.alert.repository.AlertRepository;
import com.tarpa.tourism.alert.service.AlertNotificationService;
import com.tarpa.tourism.booking.entity.Booking;
import com.tarpa.tourism.notification.entity.Notification;
import com.tarpa.tourism.notification.repository.NotificationRepository;
import com.tarpa.tourism.email.service.EmailService;
import com.tarpa.tourism.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AlertNotificationServiceImpl implements AlertNotificationService {

    private final AlertRepository alertRepository;
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final com.tarpa.tourism.invoice.service.PdfInvoiceService pdfInvoiceService;
    private final com.tarpa.tourism.whatsapp.service.WhatsAppService whatsAppService;

    // This pulls the email from your application.properties file
    @Value("${tourism.email.cc}")
    private String ccEmail;

    @Override
    public void processAlert(Long alertId) {

        // ==========================
        // FIND ALERT
        // ==========================

        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Alert not found with id: " + alertId
                        )
                );

        // ==========================
        // CHECK ALERT STATUS
        // ==========================

        if (!alert.getActive()) {
            throw new IllegalStateException(
                    "Alert is inactive: " + alert.getAlertCode()
            );
        }

        if ("PROCESSED".equalsIgnoreCase(alert.getStatus())) {
            throw new IllegalStateException(
                    "Alert has already been processed: " + alert.getAlertCode()
            );
        }

        // ==========================
        // GET BOOKING
        // ==========================

        Booking booking = alert.getBooking();

        // ==========================
        // GET CLIENT EMAIL
        // ==========================

        String clientEmail = booking.getClient().getEmail();

        if (clientEmail == null || clientEmail.isBlank()) {
            throw new IllegalStateException(
                    "Client email not found for booking: " + booking.getBookingCode()
            );
        }

        // ==========================
        // CREATE NOTIFICATION
        // ==========================

        String notificationCode = "NOT-" + alert.getAlertCode();

        Notification notification = Notification.builder()
                .notificationCode(notificationCode)
                .booking(booking)
                .type(alert.getType())
                .title(alert.getTitle())
                .message(alert.getMessage())
                .scheduledAt(alert.getScheduledAt())
                .status("PENDING")
                .active(true)
                .build();

        // This is the line that was missing!
        Notification savedNotification = notificationRepository.save(notification);

        // ==========================
        // SEND EMAIL (BEAUTIFUL HTML)
        // ==========================

// ==========================
        // GENERATE ATTACHMENT (IF NEEDED)
        // ==========================
        byte[] invoicePdf = null;
        String fileName = null;

        // Attach the invoice if it's a payment reminder, payment receipt, or booking confirmation
        if ("PAYMENT_REMINDER".equals(alert.getType()) || "PAYMENT_RECEIVED".equals(alert.getType())) {
            invoicePdf = pdfInvoiceService.generateInvoice(booking);
            fileName = "Invoice-" + booking.getBookingCode() + ".pdf";
        }

        // ==========================
        // SEND EMAIL (HTML + OPTIONAL PDF)
        // ==========================
        if (invoicePdf != null) {
            emailService.sendHtmlEmailWithCcAndAttachment(
                    clientEmail,
                    ccEmail,
                    savedNotification.getTitle(),
                    savedNotification.getTitle(),
                    savedNotification.getMessage(),
                    invoicePdf,
                    fileName
            );
        } else {
            emailService.sendHtmlEmailWithCc(
                    clientEmail,
                    ccEmail,
                    savedNotification.getTitle(),
                    savedNotification.getTitle(),
                    savedNotification.getMessage()
            );

            // ==========================
            // SEND WHATSAPP (FOR URGENT ALERTS)
            // ==========================

            // We only send WhatsApp for urgent physical logistics, not standard billing
            if ("AIRPORT_DROPOFF".equals(alert.getType())
                    || "HELI_TOUR_START".equals(alert.getType())
                    || "DEPARTURE_REMINDER".equals(alert.getType())) {

                // Note: In production, the client's phone number must include the country code (e.g., +977...)
                String clientPhone = booking.getClient().getPhone();

                if (clientPhone != null && !clientPhone.isBlank()) {
                    // We send a much shorter, text-friendly version of the alert title
                    String whatsappMessage = "TARPA TOURISM ALERT: " + alert.getTitle()
                            + "\n\n" + alert.getMessage();

                     whatsAppService.sendWhatsAppMessage(clientPhone, whatsappMessage);
                }
            }
        }


        // ==========================
        // UPDATE NOTIFICATION
        // ==========================

        savedNotification.setStatus("SENT");
        savedNotification.setSentAt(LocalDateTime.now());
        notificationRepository.save(savedNotification);

        // ==========================
        // UPDATE ALERT
        // ==========================

        alert.setStatus("PROCESSED");
        alert.setProcessedAt(LocalDateTime.now());
        alertRepository.save(alert);
    }


}