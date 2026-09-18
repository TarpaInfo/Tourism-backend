package com.tarpa.tourism.payment.controller;

import com.tarpa.tourism.payment.request.PaymentRequest;
import com.tarpa.tourism.payment.response.PaymentResponse;
import com.tarpa.tourism.payment.response.PaymentSummaryResponse;
import com.tarpa.tourism.payment.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // ==========================
    // CREATE
    // ==========================

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody PaymentRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.createPayment(request));
    }

    // ==========================
    // GET ALL
    // ==========================

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {

        return ResponseEntity.ok(
                paymentService.getAllPayments()
        );
    }

    // ==========================
    // GET BY ID
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                paymentService.getPaymentById(id)
        );
    }

    // ==========================
    // GET BY PAYMENT CODE
    // ==========================

    @GetMapping("/code/{paymentCode}")
    public ResponseEntity<PaymentResponse> getPaymentByCode(
            @PathVariable String paymentCode) {

        return ResponseEntity.ok(
                paymentService.getPaymentByCode(paymentCode)
        );
    }

    // ==========================
    // GET BY BOOKING ID
    // ==========================

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByBookingId(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                paymentService.getPaymentsByBookingId(bookingId)
        );
    }

    // ==========================
    // UPDATE
    // ==========================

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentRequest request) {

        return ResponseEntity.ok(
                paymentService.updatePayment(id, request)
        );
    }

    // ==========================
    // DELETE
    // ==========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(
            @PathVariable Long id) {

        paymentService.deletePayment(id);

        return ResponseEntity.noContent().build();
    }

    // ==========================
    // PAYMENT SUMMARY
    // ==========================

    @GetMapping("/booking/{bookingId}/summary")
    public ResponseEntity<PaymentSummaryResponse> getPaymentSummary(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                paymentService.getPaymentSummary(bookingId)
        );
    }

    // ==========================
    // INSTANT PAYMENT SUCCESS TRIGGER (PDF RECEIPT)
    // ==========================
    @PostMapping("/{bookingId}/success")
    public ResponseEntity<String> markPaymentSuccessful(
            @PathVariable Long bookingId,
            @RequestParam Double amount) {

        String response = paymentService.processPaymentSuccess(bookingId, amount);
        return ResponseEntity.ok(response);
    }
}