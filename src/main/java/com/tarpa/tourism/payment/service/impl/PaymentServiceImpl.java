package com.tarpa.tourism.payment.service.impl;

import com.tarpa.tourism.alert.service.AlertService;
import com.tarpa.tourism.booking.entity.Booking;
import com.tarpa.tourism.booking.repository.BookingRepository;
import com.tarpa.tourism.exception.DuplicateResourceException;
import com.tarpa.tourism.exception.ResourceNotFoundException;
import com.tarpa.tourism.payment.entity.Payment;
import com.tarpa.tourism.payment.repository.PaymentRepository;
import com.tarpa.tourism.payment.request.PaymentRequest;
import com.tarpa.tourism.payment.response.PaymentResponse;
import com.tarpa.tourism.payment.response.PaymentSummaryResponse;
import com.tarpa.tourism.payment.service.PaymentService;
import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final AlertService alertService;

    // ==========================
    // CREATE
    // ==========================

    private void updateBookingPaymentStatus(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found with id: " + bookingId
                        )
                );

        List<Payment> payments =
                paymentRepository.findByBookingId(bookingId);

        BigDecimal totalPaid = payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal bookingTotal = booking.getTotalAmount();

        String paymentStatus;

        if (totalPaid.compareTo(BigDecimal.ZERO) == 0) {

            paymentStatus = "UNPAID";

        } else if (totalPaid.compareTo(bookingTotal) >= 0) {

            paymentStatus = "PAID";

        } else {

            paymentStatus = "PARTIAL";
        }

        booking.setPaymentStatus(paymentStatus);

        bookingRepository.save(booking);
    }


    @Override
    public PaymentResponse createPayment(PaymentRequest request) {

        if (paymentRepository.existsByPaymentCode(
                request.getPaymentCode())) {

            throw new DuplicateResourceException(
                    "Payment code already exists: "
                            + request.getPaymentCode()
            );
        }

        Booking booking = bookingRepository.findById(
                request.getBookingId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Booking not found with id: "
                                + request.getBookingId()
                )
        );

        Payment payment = Payment.builder()
                .paymentCode(request.getPaymentCode())
                .booking(booking)
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .paymentMethod(request.getPaymentMethod())
                .transactionReference(
                        request.getTransactionReference())
                .paymentStatus(
                        request.getPaymentStatus() != null
                                ? request.getPaymentStatus()
                                : "PENDING")
                .remarks(request.getRemarks())
                .active(request.getActive() != null
                        ? request.getActive()
                        : true)
                .build();

        Payment saved = paymentRepository.save(payment);

        updateBookingPaymentStatus(
                booking.getId()
        );

        return mapToResponse(saved);
    }

    // ==========================
    // GET ALL
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================
    // GET BY ID
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with id: " + id
                        )
                );

        return mapToResponse(payment);
    }

    // ==========================
    // GET BY PAYMENT CODE
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByCode(
            String paymentCode) {

        Payment payment = paymentRepository
                .findByPaymentCode(paymentCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with code: "
                                        + paymentCode
                        )
                );

        return mapToResponse(payment);
    }

    // ==========================
    // GET BY BOOKING ID
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByBookingId(
            Long bookingId) {

        if (!bookingRepository.existsById(bookingId)) {

            throw new ResourceNotFoundException(
                    "Booking not found with id: " + bookingId
            );
        }

        return paymentRepository
                .findByBookingId(bookingId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================
    // UPDATE
    // ==========================

    @Override
    public PaymentResponse updatePayment(
            Long id,
            PaymentRequest request) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with id: " + id
                        )
                );

        Payment existingPayment =
                paymentRepository.findByPaymentCode(
                        request.getPaymentCode()
                ).orElse(null);

        if (existingPayment != null
                && !existingPayment.getId().equals(id)) {

            throw new DuplicateResourceException(
                    "Payment code already exists: "
                            + request.getPaymentCode()
            );
        }

        Booking booking = bookingRepository.findById(
                request.getBookingId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Booking not found with id: "
                                + request.getBookingId()
                )
        );

        payment.setPaymentCode(
                request.getPaymentCode());

        payment.setBooking(booking);

        payment.setAmount(
                request.getAmount());

        payment.setCurrency(
                request.getCurrency());

        payment.setPaymentMethod(
                request.getPaymentMethod());

        payment.setTransactionReference(
                request.getTransactionReference());

        if (request.getPaymentStatus() != null) {
            payment.setPaymentStatus(
                    request.getPaymentStatus());
        }

        payment.setRemarks(
                request.getRemarks());

        if (request.getActive() != null) {
            payment.setActive(
                    request.getActive());
        }

        Payment updated =
                paymentRepository.save(payment);

        updateBookingPaymentStatus(
                booking.getId()
        );

        return mapToResponse(updated);
    }

    // ==========================
    // DELETE
    // ==========================

    @Override
    public void deletePayment(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with id: " + id
                        )
                );

        Long bookingId = payment.getBooking().getId();

        paymentRepository.delete(payment);

        updateBookingPaymentStatus(bookingId);
    }

    // ==========================
    // ENTITY → RESPONSE
    // ==========================

    private PaymentResponse mapToResponse(
            Payment payment) {

        Booking booking = payment.getBooking();

        return PaymentResponse.builder()
                .id(payment.getId())
                .paymentCode(
                        payment.getPaymentCode())

                .bookingId(
                        booking.getId())

                .bookingCode(
                        booking.getBookingCode())

                .amount(
                        payment.getAmount())

                .currency(
                        payment.getCurrency())

                .paymentMethod(
                        payment.getPaymentMethod())

                .transactionReference(
                        payment.getTransactionReference())

                .paymentStatus(
                        payment.getPaymentStatus())

                .paymentDate(
                        payment.getPaymentDate())

                .remarks(
                        payment.getRemarks())

                .active(
                        payment.getActive())

                .createdAt(
                        payment.getCreatedAt())

                .updatedAt(
                        payment.getUpdatedAt())

                .build();
    }

    // ==========================
// PAYMENT SUMMARY
// ==========================

    @Override
    @Transactional(readOnly = true)
    public PaymentSummaryResponse getPaymentSummary(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found with id: " + bookingId
                        )
                );

        List<Payment> payments =
                paymentRepository.findByBookingId(bookingId);

        BigDecimal totalPaid = payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal bookingTotal = booking.getTotalAmount();

        BigDecimal remainingBalance =
                bookingTotal.subtract(totalPaid);

        String paymentStatus;

        if (totalPaid.compareTo(BigDecimal.ZERO) == 0) {

            paymentStatus = "UNPAID";

        } else if (totalPaid.compareTo(bookingTotal) >= 0) {

            paymentStatus = "PAID";

        } else {

            paymentStatus = "PARTIAL";
        }

        return PaymentSummaryResponse.builder()
                .bookingId(booking.getId())
                .bookingCode(booking.getBookingCode())
                .bookingTotal(bookingTotal)
                .totalPaid(totalPaid)
                .remainingBalance(remainingBalance)
                .paymentStatus(paymentStatus)
                .currency(booking.getCurrency())
                .build();
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public String processPaymentSuccess(Long bookingId, Double amountPaid) {

        // 1. Find the booking
        com.tarpa.tourism.booking.entity.Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new com.tarpa.tourism.exception.ResourceNotFoundException("Booking not found: " + bookingId));

        if ("PAID".equalsIgnoreCase(booking.getPaymentStatus())) {
            return "Booking is already fully paid.";
        }

        // 2. Update the booking status to PAID
        booking.setPaymentStatus("PAID");
        bookingRepository.save(booking);

        // 3. Generate the instant Alert
        String alertCode = "ALT-RECEIPT-" + booking.getBookingCode() + "-" + System.currentTimeMillis();

        com.tarpa.tourism.alert.request.AlertRequest alertRequest = com.tarpa.tourism.alert.request.AlertRequest.builder()
                .alertCode(alertCode)
                .bookingId(booking.getId())
                .type(com.tarpa.tourism.constant.AlertType.PAYMENT_RECEIVED)
                .title("Payment Receipt & Confirmation")
                .message("Dear " + booking.getClient().getFirstName() + ",\n\n"
                        + "Great news! We have successfully received your payment of "
                        + booking.getCurrency() + " " + amountPaid + " for booking " + booking.getBookingCode() + ". "
                        + "Your trip is now fully confirmed and paid.\n\n"
                        + "We have attached your official PDF invoice to this email for your records. "
                        + "We cannot wait to welcome you to the Himalayas!")
                .scheduledAt(java.time.LocalDateTime.now()) // Scheduled for RIGHT NOW
                .status("PENDING")
                .active(true)
                .build();

        alertService.createAlert(alertRequest);

        return "Payment processed successfully. Receipt alert created and PDF will be attached!";
    }
}