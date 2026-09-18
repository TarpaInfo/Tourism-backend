package com.tarpa.tourism.alert.controller;

import com.tarpa.tourism.alert.request.AlertRequest;
import com.tarpa.tourism.alert.response.AlertResponse;
import com.tarpa.tourism.alert.service.AlertNotificationService;
import com.tarpa.tourism.alert.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;
    private final AlertNotificationService alertNotificationService;

    // ==========================
    // CREATE ALERT
    // ==========================

    @PostMapping
    public ResponseEntity<AlertResponse> createAlert(
            @RequestBody AlertRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alertService.createAlert(request));
    }

    // ==========================
    // GET ALL ALERTS
    // ==========================

    @GetMapping
    public ResponseEntity<List<AlertResponse>> getAllAlerts() {

        return ResponseEntity.ok(
                alertService.getAllAlerts()
        );
    }

    // ==========================
    // GET ALERT BY ID
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<AlertResponse> getAlertById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                alertService.getAlertById(id)
        );
    }

    // ==========================
    // GET ALERT BY CODE
    // ==========================

    @GetMapping("/code/{alertCode}")
    public ResponseEntity<AlertResponse> getAlertByCode(
            @PathVariable String alertCode) {

        return ResponseEntity.ok(
                alertService.getAlertByCode(alertCode)
        );
    }

    // ==========================
    // GET ALERTS BY BOOKING
    // ==========================

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<AlertResponse>> getAlertsByBookingId(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                alertService.getAlertsByBookingId(bookingId)
        );
    }

    // ==========================
    // GET ALERTS BY STATUS
    // ==========================

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AlertResponse>> getAlertsByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                alertService.getAlertsByStatus(status)
        );
    }

    // ==========================
    // GET ALERTS BY TYPE
    // ==========================

    @GetMapping("/type/{type}")
    public ResponseEntity<List<AlertResponse>> getAlertsByType(
            @PathVariable String type) {

        return ResponseEntity.ok(
                alertService.getAlertsByType(type)
        );
    }

    @PostMapping("/{id}/process")
    public ResponseEntity<String> processAlert(
            @PathVariable Long id) {

        alertNotificationService.processAlert(id);

        return ResponseEntity.ok(
                "Alert processed and notification email sent successfully."
        );
    }

    // ==========================
    // UPDATE ALERT
    // ==========================

    @PutMapping("/{id}")
    public ResponseEntity<AlertResponse> updateAlert(
            @PathVariable Long id,
            @RequestBody AlertRequest request) {

        return ResponseEntity.ok(
                alertService.updateAlert(id, request)
        );
    }

    // ==========================
    // DELETE ALERT
    // ==========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(
            @PathVariable Long id) {

        alertService.deleteAlert(id);

        return ResponseEntity.noContent().build();
    }
}