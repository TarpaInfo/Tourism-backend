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
public class FeedbackAlertScheduler {

    private final BookingRepository bookingRepository;
    private final AlertRepository alertRepository;
    private final AlertService alertService;

    // =========================================================
    // POST-TRIP FEEDBACK (2 Days After Trip Ends)
    // Runs every day at 10:00 AM
    // =========================================================

    @Scheduled(cron = "0 0 10 * * *")
    @Transactional
    public void createFeedbackAlerts() {

        LocalDate today = LocalDate.now();
        List<Booking> activeBookings = bookingRepository.findByBookingStatus("CONFIRMED");

        for (Booking booking : activeBookings) {

            if (booking.getTravelDate() != null && booking.getTourPackage() != null) {

                Integer duration = booking.getTourPackage().getDurationDays();
                if (duration == null) duration = 0;

                // End Date = Travel Date + Duration Days
                // Feedback Date = End Date + 2 Days
                LocalDate feedbackDate = booking.getTravelDate().plusDays(duration).plusDays(2);

                if (feedbackDate.equals(today)) {

                    String alertCode = "ALT-FEEDBACK-" + booking.getBookingCode();
                    if (alertRepository.existsByAlertCode(alertCode)) continue;

                    AlertRequest alertRequest = AlertRequest.builder()
                            .alertCode(alertCode)
                            .bookingId(booking.getId())
                            .type(AlertType.TRIP_FEEDBACK)
                            .title("How was your trip to Nepal?")
                            .message("Dear " + booking.getClient().getFirstName()
                                    + ", we hope you have settled back in safely! It was an honor hosting you for the "
                                    + booking.getTourPackage().getPackageName() + ". "
                                    + "Your feedback means the world to us and helps future travelers. "
                                    + "Could you please take 2 minutes to leave us a review? Thank you again for choosing us!")
                            .scheduledAt(LocalDateTime.now())
                            .status("PENDING")
                            .active(true)
                            .build();

                    try {
                        alertService.createAlert(alertRequest);
                        System.out.println("Feedback alert created for: " + booking.getBookingCode());
                    } catch (Exception e) {
                        System.out.println("Failed to create feedback alert for: " + booking.getBookingCode());
                    }
                }
            }
        }
    }
}