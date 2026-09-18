package com.tarpa.tourism.feedback.scheduler;

import com.tarpa.tourism.alert.request.AlertRequest;
import com.tarpa.tourism.alert.service.AlertService;
import com.tarpa.tourism.booking.entity.Booking;
import com.tarpa.tourism.booking.repository.BookingRepository;
import com.tarpa.tourism.constant.AlertType;
import com.tarpa.tourism.feedback.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component("automatedFeedbackAlertScheduler")
@RequiredArgsConstructor
public class FeedbackAlertScheduler {

    private final BookingRepository bookingRepository;
    private final FeedbackRepository feedbackRepository;
    private final AlertService alertService;

    // Runs every day at 11:00 AM
    @Scheduled(cron = "0 0 11 * * *")
    @Transactional
    public void scheduleFeedbackRequests() {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        // Fetch active bookings
        List<Booking> activeBookings = bookingRepository.findAll();

        for (Booking booking : activeBookings) {
            if (!booking.getActive() || booking.getTourPackage() == null) {
                continue;
            }

            // Calculate tour end date based on package duration (assuming getDurationDays() on TourPackage)
            int durationDays = booking.getTourPackage().getDurationDays() != null
                    ? booking.getTourPackage().getDurationDays()
                    : 1;

            LocalDate tourEndDate = booking.getTravelDate().plusDays(durationDays);

            if (tourEndDate.isEqual(yesterday) && !feedbackRepository.existsByBookingId(booking.getId())) {
                String alertCode = "ALT-FEEDBACK-" + booking.getBookingCode() + "-" + System.currentTimeMillis();

                String feedbackUrl = "https://tarpatourism.com/feedback?booking=" + booking.getBookingCode();

                AlertRequest alertRequest = AlertRequest.builder()
                        .alertCode(alertCode)
                        .bookingId(booking.getId())
                        .type(AlertType.TRIP_FEEDBACK)
                        .title("How was your trip with Tarpa Tourism?")
                        .message("Dear " + booking.getClient().getFirstName() + ",\n\n"
                                + "Welcome back! We hope you had an unforgettable experience exploring "
                                + booking.getTourPackage().getPackageName() + ".\n\n"
                                + "Your feedback helps us continuously elevate our treks and tours. "
                                + "Please take 30 seconds to rate your adventure: " + feedbackUrl + "\n\n"
                                + "Thank you for traveling with us!")
                        .scheduledAt(LocalDateTime.now())
                        .status("PENDING")
                        .active(true)
                        .build();

                alertService.createAlert(alertRequest);
            }
        }
    }
}