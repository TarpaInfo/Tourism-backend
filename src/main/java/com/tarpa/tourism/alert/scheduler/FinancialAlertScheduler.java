package com.tarpa.tourism.alert.scheduler;

import com.tarpa.tourism.alert.repository.AlertRepository;
import com.tarpa.tourism.alert.request.AlertRequest;
import com.tarpa.tourism.alert.service.AlertService;
import com.tarpa.tourism.booking.entity.Booking;
import com.tarpa.tourism.booking.repository.BookingRepository;
import com.tarpa.tourism.constant.AlertType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FinancialAlertScheduler {

    private final BookingRepository bookingRepository;
    private final AlertRepository alertRepository;
    private final AlertService alertService;

    // =========================================================
    // PAYMENT REMINDER (7 Days Before Travel)
    // Runs every day at 09:00 AM
    // =========================================================

    @Scheduled(cron = "0 0 9 * * *")
    @Transactional
    public void createPaymentReminders() {

        LocalDate oneWeekAway = LocalDate.now().plusDays(7);
        List<Booking> activeBookings = bookingRepository.findByBookingStatus("CONFIRMED");

        for (Booking booking : activeBookings) {

            // Check if trip is exactly 7 days away AND payment is missing
            if (booking.getTravelDate() != null && booking.getTravelDate().equals(oneWeekAway)) {

                String paymentStatus = booking.getPaymentStatus() != null ? booking.getPaymentStatus().toUpperCase() : "UNPAID";

                if (paymentStatus.equals("UNPAID") || paymentStatus.equals("PARTIAL")) {

                    String alertCode = "ALT-PAY-REM-" + booking.getBookingCode();
                    if (alertRepository.existsByAlertCode(alertCode)) continue;

                    AlertRequest alertRequest = AlertRequest.builder()
                            .alertCode(alertCode)
                            .bookingId(booking.getId())
                            .type(AlertType.PAYMENT_REMINDER)
                            .title("Upcoming Trip Payment Reminder")
                            .message("Dear " + booking.getClient().getFirstName()
                                    + ", we are so excited for your trip starting on " + booking.getTravelDate() + ". "
                                    + "This is a friendly reminder that your current payment status is " + paymentStatus + ". "
                                    + "Please clear any outstanding balance before your arrival. Let us know if you need assistance!")
                            .scheduledAt(LocalDateTime.now())
                            .status("PENDING")
                            .active(true)
                            .build();

                    try {
                        alertService.createAlert(alertRequest);
                        System.out.println("Payment reminder created for: " + booking.getBookingCode());
                    } catch (Exception e) {
                        System.out.println("Failed to create payment reminder for: " + booking.getBookingCode());
                    }
                }
            }
        }
    }
}