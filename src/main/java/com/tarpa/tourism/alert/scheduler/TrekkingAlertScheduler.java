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
public class TrekkingAlertScheduler {

    private final BookingRepository bookingRepository;
    private final AlertRepository alertRepository;
    private final AlertService alertService;

    // =========================================================
    // TREKKING START (MORNING OF THE TREK)
    // Runs every day at 06:00 AM
    // =========================================================

    @Scheduled(cron = "0 0 6 * * *")
    @Transactional
    public void createTrekkingStartAlerts() {

        LocalDate today = LocalDate.now();
        List<Booking> activeBookings = bookingRepository.findByBookingStatus("CONFIRMED");

        for (Booking booking : activeBookings) {

            // Process if the travel date is EXACTLY today
            if (booking.getTravelDate() != null && booking.getTravelDate().equals(today)) {

                String alertCode = "ALT-TREK-START-" + booking.getBookingCode();

                if (alertRepository.existsByAlertCode(alertCode)) {
                    continue;
                }

                AlertRequest alertRequest = AlertRequest.builder()
                        .alertCode(alertCode)
                        .bookingId(booking.getId())
                        .type(AlertType.TREKKING_START)
                        .title("Your Trekking Adventure Begins Today!")
                        .message("Dear " + booking.getClient().getFirstName()
                                + ", today is the day! Your adventure for "
                                + booking.getTourPackage().getPackageName()
                                + " officially begins. Please ensure your gear is packed and ready. "
                                + "Your guide will meet you as scheduled. Have a safe and unforgettable trek!")
                        .scheduledAt(LocalDateTime.now())
                        .status("PENDING")
                        .active(true)
                        .build();

                try {
                    alertService.createAlert(alertRequest);
                    System.out.println("Trekking start alert created for: " + booking.getBookingCode());
                } catch (Exception e) {
                    System.out.println("Failed to create trekking start alert for: " + booking.getBookingCode());
                }
            }
        }
    }

    // =========================================================
    // PEAK CLIMBING & EXPEDITION START (MORNING OF)
    // Runs every day at 05:00 AM (Early start for climbers!)
    // =========================================================

    @Scheduled(cron = "0 0 5 * * *")
    @Transactional
    public void createClimbingAndExpeditionStartAlerts() {

        LocalDate today = LocalDate.now();
        List<Booking> activeBookings = bookingRepository.findByBookingStatus("CONFIRMED");

        for (Booking booking : activeBookings) {

            if (booking.getTravelDate() != null && booking.getTravelDate().equals(today)) {

                String pName = booking.getTourPackage().getPackageName().toUpperCase();
                String pType = booking.getTourPackage().getPackageType() != null ?
                        booking.getTourPackage().getPackageType().toUpperCase() : "";

                boolean isExpedition = pName.contains("EXPEDITION") || pType.contains("EXPEDITION");
                boolean isPeak = pName.contains("PEAK") || pType.contains("PEAK");

                if (!isExpedition && !isPeak) continue; // Skip standard treks (handled by your other method)

                String type = isExpedition ? AlertType.EXPEDITION_START : AlertType.PEAK_CLIMBING_START;
                String alertCode = "ALT-" + type + "-" + booking.getBookingCode();

                if (alertRepository.existsByAlertCode(alertCode)) continue;

                AlertRequest alertRequest = AlertRequest.builder()
                        .alertCode(alertCode)
                        .bookingId(booking.getId())
                        .type(type)
                        .title("Your " + (isExpedition ? "Expedition" : "Peak Climbing") + " Begins Today!")
                        .message("Dear " + booking.getClient().getFirstName()
                                + ", the day has arrived! Your " + booking.getTourPackage().getPackageName()
                                + " begins today. We wish you immense strength, good weather, and a safe summit. "
                                + "Our team is with you every step of the way. Good luck!")
                        .scheduledAt(LocalDateTime.now())
                        .status("PENDING")
                        .active(true)
                        .build();

                try {
                    alertService.createAlert(alertRequest);
                    System.out.println(type + " alert created for: " + booking.getBookingCode());
                } catch (Exception e) {
                    System.out.println("Failed to create " + type + " alert for: " + booking.getBookingCode());
                }
            }
        }
    }

    // =========================================================
    // RETURN ALERTS (TREKKING & EXPEDITIONS)
    // Runs every day at 05:00 PM (17:00) on the final day
    // =========================================================

    @Scheduled(cron = "0 0 17 * * *")
    @Transactional
    public void createAdventureReturnAlerts() {

        LocalDate today = LocalDate.now();
        List<Booking> activeBookings = bookingRepository.findByBookingStatus("CONFIRMED");

        for (Booking booking : activeBookings) {

            Integer duration = booking.getTourPackage().getDurationDays();
            if (duration == null) duration = 0;

            // Return Date = Travel Date + Duration Days
            LocalDate returnDate = booking.getTravelDate().plusDays(duration);

            if (returnDate.equals(today)) {

                String pName = booking.getTourPackage().getPackageName().toUpperCase();
                String pType = booking.getTourPackage().getPackageType() != null ?
                        booking.getTourPackage().getPackageType().toUpperCase() : "";

                boolean isExpedition = pName.contains("EXPEDITION") || pType.contains("EXPEDITION");

                String type = isExpedition ? AlertType.EXPEDITION_RETURN : AlertType.TREKKING_RETURN;
                String alertCode = "ALT-RETURN-" + booking.getBookingCode();

                if (alertRepository.existsByAlertCode(alertCode)) continue;

                AlertRequest alertRequest = AlertRequest.builder()
                        .alertCode(alertCode)
                        .bookingId(booking.getId())
                        .type(type)
                        .title("Welcome Back from your Adventure!")
                        .message("Dear " + booking.getClient().getFirstName()
                                + ", congratulations on completing the " + booking.getTourPackage().getPackageName() + "! "
                                + "We hope you had a safe and incredible journey. Please rest well, and we would love to hear "
                                + "about your experience soon.")
                        .scheduledAt(LocalDateTime.now())
                        .status("PENDING")
                        .active(true)
                        .build();

                try {
                    alertService.createAlert(alertRequest);
                    System.out.println(type + " alert created for: " + booking.getBookingCode());
                } catch (Exception e) {
                    System.out.println("Failed to create return alert for: " + booking.getBookingCode());
                }
            }
        }
    }
}