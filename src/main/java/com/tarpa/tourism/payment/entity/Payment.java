package com.tarpa.tourism.payment.entity;

import com.tarpa.tourism.booking.entity.Booking;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================
    // PAYMENT CODE
    // ==========================

    @Column(nullable = false, unique = true)
    private String paymentCode;

    // ==========================
    // BOOKING
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    // ==========================
    // PAYMENT INFORMATION
    // ==========================

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    private String currency;

    private String paymentMethod;

    private String transactionReference;

    // ==========================
    // PAYMENT STATUS
    // ==========================

    @Builder.Default
    @Column(nullable = false)
    private String paymentStatus = "PENDING";

    // ==========================
    // PAYMENT DATE
    // ==========================

    private LocalDateTime paymentDate;

    // ==========================
    // REMARKS
    // ==========================

    @Column(columnDefinition = "TEXT")
    private String remarks;

    // ==========================
    // STATUS
    // ==========================

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    // ==========================
    // TIMESTAMPS
    // ==========================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ==========================
    // PRE-PERSIST
    // ==========================

    @PrePersist
    protected void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (paymentStatus == null) {
            paymentStatus = "PENDING";
        }

        if (paymentDate == null) {
            paymentDate = LocalDateTime.now();
        }

        if (active == null) {
            active = true;
        }
    }

    // ==========================
    // PRE-UPDATE
    // ==========================

    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}