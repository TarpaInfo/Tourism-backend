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
public class PaymentAlertScheduler {

    private final BookingRepository bookingRepository;
    private final AlertRepository alertRepository;
    private final AlertService alertService;

    // =========================================================
    // PAYMENT REMINDER (7 DAYS BEFORE TRIP)
    // Runs every day at 08:00 AM
    // =========================================================

    @Scheduled(cron = "0 0 8 * * *")
    @Transactional
    public void createPaymentReminders() {

        // Find trips that are starting exactly 7 days from today
        LocalDate oneWeekAway = LocalDate.now().plusDays(7);
        List<Booking> activeBookings = bookingRepository.findByBookingStatus("CONFIRMED");

        for (Booking booking : activeBookings) {

            // Only process if travel date is in 7 days AND payment is not fully paid
            if (booking.getTravelDate() != null
                    && booking.getTravelDate().equals(oneWeekAway)
                    && ("UNPAID".equalsIgnoreCase(booking.getPaymentStatus())
                    || "PARTIAL".equalsIgnoreCase(booking.getPaymentStatus()))) {

                String alertCode = "ALT-PAY-" + booking.getBookingCode();

                if (alertRepository.existsByAlertCode(alertCode)) continue;

                AlertRequest alertRequest = AlertRequest.builder()
                        .alertCode(alertCode)
                        .bookingId(booking.getId())
                        .type(AlertType.PAYMENT_REMINDER) // Assuming this is defined in AlertType
                        .title("Outstanding Payment Reminder")
                        .message("Dear " + booking.getClient().getFirstName()
                                + ", this is a reminder that your upcoming trip starting on "
                                + booking.getTravelDate() + " has an outstanding balance. "
                                + "Your current payment status is " + booking.getPaymentStatus()
                                + ". Please complete your payment to secure your arrangements.")
                        .scheduledAt(LocalDateTime.now())
                        .status("PENDING")
                        .active(true)
                        .build();

                try {
                    alertService.createAlert(alertRequest);
                    System.out.println("Payment reminder alert created for: " + booking.getBookingCode());
                } catch (Exception e) {
                    System.out.println("Failed to create payment reminder for: " + booking.getBookingCode());
                }
            }
        }
    }
}