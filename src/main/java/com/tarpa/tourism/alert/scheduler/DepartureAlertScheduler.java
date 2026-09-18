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
public class DepartureAlertScheduler {

    private final BookingRepository bookingRepository;
    private final AlertRepository alertRepository;
    private final AlertService alertService;

    // =========================================================
    // DEPARTURE REMINDER (1 Day Before International Flight)
    // Runs every day at 02:00 PM (14:00)
    // =========================================================

    @Scheduled(cron = "0 0 14 * * *")
    @Transactional
    public void createDepartureReminders() {

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Booking> activeBookings = bookingRepository.findByBookingStatus("CONFIRMED");

        for (Booking booking : activeBookings) {

            // Check if they have a departure flight scheduled for tomorrow
            if (booking.getDepartureDateTime() != null
                    && booking.getDepartureDateTime().toLocalDate().equals(tomorrow)) {

                String alertCode = "ALT-DEP-REM-" + booking.getBookingCode();
                if (alertRepository.existsByAlertCode(alertCode)) continue;

                AlertRequest alertRequest = AlertRequest.builder()
                        .alertCode(alertCode)
                        .bookingId(booking.getId())
                        .type(AlertType.DEPARTURE_REMINDER)
                        .title("Your Departure is Tomorrow")
                        .message("Dear " + booking.getClient().getFirstName()
                                + ", this is a reminder that your international departure flight is scheduled for tomorrow at "
                                + booking.getDepartureDateTime().toLocalTime() + ". "
                                + "Please ensure your bags are packed and you have your passport ready. Safe travels!")
                        .scheduledAt(LocalDateTime.now())
                        .status("PENDING")
                        .active(true)
                        .build();

                try {
                    alertService.createAlert(alertRequest);
                    System.out.println("Departure reminder alert created for: " + booking.getBookingCode());
                } catch (Exception e) {
                    System.out.println("Failed to create departure reminder for: " + booking.getBookingCode());
                }
            }
        }
    }

    // =========================================================
    // AIRPORT DROPOFF (Day of Departure)
    // Runs every day at 08:00 AM
    // =========================================================

    @Scheduled(cron = "0 0 8 * * *")
    @Transactional
    public void createAirportDropoffAlerts() {

        LocalDate today = LocalDate.now();
        List<Booking> activeBookings = bookingRepository.findByBookingStatus("CONFIRMED");

        for (Booking booking : activeBookings) {

            // Check if departure is today AND they requested an airport transfer
            if (booking.getDepartureDateTime() != null
                    && booking.getDepartureDateTime().toLocalDate().equals(today)
                    && Boolean.TRUE.equals(booking.getRequiresAirportTransfer())) {

                String alertCode = "ALT-DROPOFF-" + booking.getBookingCode();
                if (alertRepository.existsByAlertCode(alertCode)) continue;

                AlertRequest alertRequest = AlertRequest.builder()
                        .alertCode(alertCode)
                        .bookingId(booking.getId())
                        .type(AlertType.AIRPORT_DROPOFF)
                        .title("Your Airport Drop-off Today")
                        .message("Dear " + booking.getClient().getFirstName()
                                + ", today is your departure day. Your airport drop-off is scheduled. "
                                + "Our driver will meet you to ensure you arrive at the airport on time for your "
                                + booking.getDepartureDateTime().toLocalTime() + " flight. Thank you for choosing us to explore Nepal!")
                        .scheduledAt(LocalDateTime.now())
                        .status("PENDING")
                        .active(true)
                        .build();

                try {
                    alertService.createAlert(alertRequest);
                    System.out.println("Airport drop-off alert created for: " + booking.getBookingCode());
                } catch (Exception e) {
                    System.out.println("Failed to create airport drop-off alert for: " + booking.getBookingCode());
                }
            }
        }
    }
}