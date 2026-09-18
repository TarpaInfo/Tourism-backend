package com.tarpa.tourism.alert.scheduler;

import com.tarpa.tourism.alert.entity.Alert;
import com.tarpa.tourism.alert.repository.AlertRepository;
import com.tarpa.tourism.alert.service.AlertNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import com.tarpa.tourism.hotel.repository.HotelBookingRepository;
import com.tarpa.tourism.hotel.entity.HotelBooking;
import com.tarpa.tourism.alert.service.AlertService;
import com.tarpa.tourism.alert.request.AlertRequest;
import com.tarpa.tourism.constant.AlertType;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlertScheduler {

    private final AlertRepository alertRepository;
    private final AlertNotificationService alertNotificationService;
    private final HotelBookingRepository hotelBookingRepository;
    private final AlertService alertService;

    // ==========================================
    // PROCESS DUE ALERTS
    // ==========================================

    @Scheduled(fixedRate = 60000)
    public void processDueAlerts() {

        LocalDateTime now = LocalDateTime.now();

        List<Alert> alerts =
                alertRepository.findByStatusAndScheduledAtLessThanEqual(
                        "PENDING",
                        now
                );

        for (Alert alert : alerts) {

            try {

                log.info(
                        "Processing alert: {}",
                        alert.getAlertCode()
                );

                alertNotificationService.processAlert(
                        alert.getId()
                );

            } catch (Exception e) {

                log.error(
                        "Failed to process alert: {}",
                        alert.getAlertCode(),
                        e
                );
            }
        }
    }

    // ==========================================
    // GENERATE HOTEL CHECK-IN REMINDERS
    // ==========================================

    // Runs every minute for testing. For production, use cron = "0 0 8 * * ?" (8 AM daily)
    @Scheduled(fixedRate = 60000)
    public void generateHotelCheckInReminders() {

        LocalDate tomorrow = LocalDate.now().plusDays(1);

        // Find all confirmed hotel bookings checking in tomorrow
        List<HotelBooking> upcomingBookings = hotelBookingRepository
                .findByStatusAndCheckInDate("CONFIRMED", tomorrow);

        for (HotelBooking hotelBooking : upcomingBookings) {
            try {
                AlertRequest alertRequest = AlertRequest.builder()
                        .alertCode("ALT-CHK-" + hotelBooking.getHotelBookingCode())
                        .bookingId(hotelBooking.getBooking().getId())
                        .type(AlertType.HOTEL_CHECKIN_REMINDER) // Assuming this is a String in your AlertType constants
                        .title("Hotel Check-in Reminder")
                        .message("Dear " + hotelBooking.getBooking().getClient().getFirstName()
                                + ", this is a reminder for your check-in tomorrow at "
                                + hotelBooking.getHotelName() + " in " + hotelBooking.getCity() + ".")
                        .scheduledAt(LocalDateTime.now())
                        .status("PENDING")
                        .active(true)
                        .build();

                // This will fail safely with your DuplicateResourceException if the alert already exists!
                alertService.createAlert(alertRequest);

                log.info("Generated HOTEL_CHECKIN_REMINDER for: {}", hotelBooking.getHotelBookingCode());

            } catch (Exception e) {
                // We ignore duplicate errors so it doesn't crash the loop if the alert was already generated
                log.debug("Alert already exists or failed for: {}", hotelBooking.getHotelBookingCode());
            }
        }
    }
}