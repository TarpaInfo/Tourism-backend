package com.tarpa.tourism.payment.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    private String paymentCode;

    private Long bookingId;

    private BigDecimal amount;

    private String currency;

    private String paymentMethod;

    private String transactionReference;

    private String paymentStatus;

    private String remarks;

    private Boolean active;
}