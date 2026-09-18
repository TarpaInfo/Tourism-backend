package com.tarpa.tourism.payment.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentSummaryResponse {

    private Long bookingId;

    private String bookingCode;

    private BigDecimal bookingTotal;

    private BigDecimal totalPaid;

    private BigDecimal remainingBalance;

    private String paymentStatus;

    private String currency;
}