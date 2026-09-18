package com.tarpa.tourism.notification.controller;

import com.tarpa.tourism.notification.request.NotificationRequest;
import com.tarpa.tourism.notification.response.NotificationResponse;
import com.tarpa.tourism.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // ==========================
    // CREATE
    // ==========================

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(
            @RequestBody NotificationRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notificationService.createNotification(request));
    }

    // ==========================
    // GET ALL
    // ==========================

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {

        return ResponseEntity.ok(
                notificationService.getAllNotifications()
        );
    }

    // ==========================
    // GET BY ID
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotificationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                notificationService.getNotificationById(id)
        );
    }

    // ==========================
    // GET BY CODE
    // ==========================

    @GetMapping("/code/{notificationCode}")
    public ResponseEntity<NotificationResponse> getNotificationByCode(
            @PathVariable String notificationCode) {

        return ResponseEntity.ok(
                notificationService.getNotificationByCode(
                        notificationCode
                )
        );
    }

    // ==========================
    // GET BY BOOKING ID
    // ==========================

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<NotificationResponse>>
    getNotificationsByBookingId(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                notificationService.getNotificationsByBookingId(
                        bookingId
                )
        );
    }

    // ==========================
    // GET BY STATUS
    // ==========================

    @GetMapping("/status/{status}")
    public ResponseEntity<List<NotificationResponse>>
    getNotificationsByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                notificationService.getNotificationsByStatus(
                        status
                )
        );
    }

    // ==========================
    // GET BY TYPE
    // ==========================

    @GetMapping("/type/{type}")
    public ResponseEntity<List<NotificationResponse>>
    getNotificationsByType(
            @PathVariable String type) {

        return ResponseEntity.ok(
                notificationService.getNotificationsByType(
                        type
                )
        );
    }

    // ==========================
    // UPDATE
    // ==========================

    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponse> updateNotification(
            @PathVariable Long id,
            @RequestBody NotificationRequest request) {

        return ResponseEntity.ok(
                notificationService.updateNotification(
                        id,
                        request
                )
        );
    }

    // ==========================
    // DELETE
    // ==========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable Long id) {

        notificationService.deleteNotification(id);

        return ResponseEntity.noContent().build();
    }
}