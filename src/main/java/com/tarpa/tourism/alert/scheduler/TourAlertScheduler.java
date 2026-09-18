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
public class TourAlertScheduler {

    private final BookingRepository bookingRepository;
    private final AlertRepository alertRepository;
    private final AlertService alertService;

    // =========================================================
    // HELI TOUR & STANDARD TOUR START
    // Runs every day at 07:00 AM
    // =========================================================

    @Scheduled(cron = "0 0 7 * * *")
    @Transactional
    public void createTourStartAlerts() {

        LocalDate today = LocalDate.now();
        List<Booking> activeBookings = bookingRepository.findByBookingStatus("CONFIRMED");

        for (Booking booking : activeBookings) {

            if (booking.getTravelDate() != null && booking.getTravelDate().equals(today)) {

                String pName = booking.getTourPackage().getPackageName().toUpperCase();
                String pType = booking.getTourPackage().getPackageType() != null ?
                        booking.getTourPackage().getPackageType().toUpperCase() : "";

                boolean isHeli = pName.contains("HELI") || pType.contains("HELI");
                boolean isTour = pName.contains("TOUR") || pType.contains("TOUR");

                if (pName.contains("TREK") || pName.contains("EXPEDITION") || pName.contains("PEAK")) continue;
                if (!isHeli && !isTour) continue;

                String type = isHeli ? AlertType.HELI_TOUR_START : AlertType.TOUR_START;
                String alertCode = "ALT-" + type + "-" + booking.getBookingCode();

                if (alertRepository.existsByAlertCode(alertCode)) continue;

                AlertRequest alertRequest = AlertRequest.builder()
                        .alertCode(alertCode)
                        .bookingId(booking.getId())
                        .type(type)
                        .title("Your " + (isHeli ? "Helicopter Tour" : "Sightseeing Tour") + " is Today!")
                        .message("Dear " + booking.getClient().getFirstName()
                                + ", get ready for an amazing day! Your " + booking.getTourPackage().getPackageName()
                                + " begins today. Please ensure you have your camera, ID, and any required gear ready. "
                                + "Our team is excited to show you the beauty of Nepal!")
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
    // HELI TOUR & STANDARD TOUR RETURN
    // Runs every day at 06:00 PM (18:00)
    // =========================================================

    @Scheduled(cron = "0 0 18 * * *")
    @Transactional
    public void createTourReturnAlerts() {

        LocalDate today = LocalDate.now();
        List<Booking> activeBookings = bookingRepository.findByBookingStatus("CONFIRMED");

        for (Booking booking : activeBookings) {

            Integer duration = booking.getTourPackage().getDurationDays();
            if (duration == null) duration = 0;

            LocalDate returnDate = booking.getTravelDate().plusDays(duration);

            if (returnDate.equals(today)) {

                String pName = booking.getTourPackage().getPackageName().toUpperCase();
                String pType = booking.getTourPackage().getPackageType() != null ?
                        booking.getTourPackage().getPackageType().toUpperCase() : "";

                boolean isHeli = pName.contains("HELI") || pType.contains("HELI");
                boolean isTour = pName.contains("TOUR") || pType.contains("TOUR");

                if (pName.contains("TREK") || pName.contains("EXPEDITION") || pName.contains("PEAK")) continue;
                if (!isHeli && !isTour) continue;

                String type = isHeli ? AlertType.HELI_TOUR_RETURN : AlertType.TOUR_RETURN;
                String alertCode = "ALT-RETURN-" + type + "-" + booking.getBookingCode();

                if (alertRepository.existsByAlertCode(alertCode)) continue;

                AlertRequest alertRequest = AlertRequest.builder()
                        .alertCode(alertCode)
                        .bookingId(booking.getId())
                        .type(type)
                        .title("Thank You for Touring With Us!")
                        .message("Dear " + booking.getClient().getFirstName()
                                + ", we hope you absolutely loved your " + booking.getTourPackage().getPackageName() + "! "
                                + "Thank you for choosing us to explore Nepal. We would love to see your photos and hear your feedback.")
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