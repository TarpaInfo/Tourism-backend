package com.tarpa.tourism.payment.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long id;

    private String paymentCode;

    // Booking information
    private Long bookingId;
    private String bookingCode;

    // Payment information
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private String transactionReference;

    // Status
    private String paymentStatus;

    // Payment date
    private LocalDateTime paymentDate;

    // Remarks
    private String remarks;

    // Status
    private Boolean active;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}