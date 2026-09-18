package com.tarpa.tourism.payment.service;

import com.tarpa.tourism.payment.request.PaymentRequest;
import com.tarpa.tourism.payment.response.PaymentResponse;
import com.tarpa.tourism.payment.response.PaymentSummaryResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);

    List<PaymentResponse> getAllPayments();

    PaymentResponse getPaymentById(Long id);

    PaymentResponse getPaymentByCode(String paymentCode);

    List<PaymentResponse> getPaymentsByBookingId(Long bookingId);

    PaymentResponse updatePayment(Long id, PaymentRequest request);

    void deletePayment(Long id);

    PaymentSummaryResponse getPaymentSummary(Long bookingId);

    String processPaymentSuccess(Long bookingId, Double amountPaid);
}