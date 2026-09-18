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
public class TransportAlertScheduler {

    private final BookingRepository bookingRepository;
    private final AlertRepository alertRepository;
    private final AlertService alertService;

    // =========================================================
    // AIRPORT TRANSFER REMINDER
    // Runs every 30 seconds for testing!
    // (Change to cron = "0 0 17 * * *" for production)
    // =========================================================

    @Scheduled(cron = "0 0 17 * * *")
    @Transactional
    public void createAirportTransferAlerts() {

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Booking> activeBookings = bookingRepository.findByBookingStatus("CONFIRMED");

        for (Booking booking : activeBookings) {

            // Only process if they require a transfer AND the transfer time is tomorrow
            if (booking.getRequiresAirportTransfer() != null
                    && booking.getRequiresAirportTransfer()
                    && booking.getTransferTime() != null
                    && booking.getTransferTime().toLocalDate().equals(tomorrow)) {

                String alertCode = "ALT-TRANS-" + booking.getBookingCode();

                if (alertRepository.existsByAlertCode(alertCode)) {
                    continue;
                }

                AlertRequest alertRequest = AlertRequest.builder()
                        .alertCode(alertCode)
                        .bookingId(booking.getId())
                        .type(AlertType.AIRPORT_TRANSFER) // Or remove .name() if your AlertType is just Strings
                        .title("Airport Transfer Scheduled")
                        .message("Dear " + booking.getClient().getFirstName()
                                + ", your airport transfer is scheduled for tomorrow at "
                                + booking.getTransferTime().toLocalTime() + ". "
                                + "Vehicle Details: " + booking.getVehicleDetails() + ".")
                        .scheduledAt(LocalDateTime.now())
                        .status("PENDING")
                        .active(true)
                        .build();

                try {
                    alertService.createAlert(alertRequest);
                    System.out.println("Airport transfer alert created for: " + booking.getBookingCode());
                } catch (Exception e) {
                    System.out.println("Failed to create transfer alert for: " + booking.getBookingCode());
                }
            }
        }
    }

    // =========================================================
    // DOMESTIC FLIGHT REMINDER
    // Runs every day at 12:00 PM (Noon)
    // =========================================================

    @Scheduled(cron = "0 0 12 * * *")
    @Transactional
    public void createDomesticFlightAlerts() {

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Booking> activeBookings = bookingRepository.findByBookingStatus("CONFIRMED");

        for (Booking booking : activeBookings) {

            // Process if flight details exist AND departure is tomorrow
            if (booking.getFlightDetails() != null
                    && !booking.getFlightDetails().trim().isEmpty()
                    && booking.getDepartureDateTime() != null
                    && booking.getDepartureDateTime().toLocalDate().equals(tomorrow)) {

                String alertCode = "ALT-FLT-" + booking.getBookingCode();

                if (alertRepository.existsByAlertCode(alertCode)) {
                    continue;
                }

                AlertRequest alertRequest = AlertRequest.builder()
                        .alertCode(alertCode)
                        .bookingId(booking.getId())
                        .type(AlertType.DOMESTIC_FLIGHT)
                        .title("Upcoming Domestic Flight")
                        .message("Dear " + booking.getClient().getFirstName()
                                + ", this is a reminder for your domestic flight tomorrow at "
                                + booking.getDepartureDateTime().toLocalTime() + ". "
                                + "Flight Details: " + booking.getFlightDetails() + ". "
                                + "Please ensure you have your tickets and ID ready.")
                        .scheduledAt(LocalDateTime.now())
                        .status("PENDING")
                        .active(true)
                        .build();

                try {
                    alertService.createAlert(alertRequest);
                    System.out.println("Domestic flight alert created for: " + booking.getBookingCode());
                } catch (Exception e) {
                    System.out.println("Failed to create flight alert for: " + booking.getBookingCode());
                }
            }
        }
    }

    // =========================================================
    // TRANSPORTATION STAFF DUTY REMINDER
    // Runs every day at 09:00 PM (21:00)
    // =========================================================

    @Scheduled(cron = "0 0 21 * * *")
    @Transactional
    public void createStaffTransportationAlerts() {

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Booking> activeBookings = bookingRepository.findByBookingStatus("CONFIRMED");

        for (Booking booking : activeBookings) {

            // Process if transfer time is tomorrow and vehicle/driver details exist
            if (booking.getTransferTime() != null
                    && booking.getTransferTime().toLocalDate().equals(tomorrow)
                    && booking.getVehicleDetails() != null
                    && !booking.getVehicleDetails().trim().isEmpty()) {

                // Using a unique alert code prefix for staff reminders
                String alertCode = "ALT-STAFF-TRANS-" + booking.getBookingCode();

                if (alertRepository.existsByAlertCode(alertCode)) {
                    continue;
                }

                AlertRequest alertRequest = AlertRequest.builder()
                        .alertCode(alertCode)
                        .bookingId(booking.getId())
                        .type(AlertType.TRANSPORTATION)
                        .title("Transport Duty Reminder for Tomorrow")
                        .message("Staff Reminder: A transport is scheduled for tomorrow at "
                                + booking.getTransferTime().toLocalTime() + ". "
                                + "Assigned Vehicle & Driver: " + booking.getVehicleDetails() + ". "
                                + "Client Name: " + booking.getClient().getFirstName() + " " + booking.getClient().getLastName() + ". "
                                + "Please ensure the vehicle is prepared and on time.")
                        .scheduledAt(LocalDateTime.now())
                        .status("PENDING")
                        .active(true)
                        .build();

                try {
                    alertService.createAlert(alertRequest);
                    System.out.println("Staff transport reminder created for: " + booking.getBookingCode());
                } catch (Exception e) {
                    System.out.println("Failed to create staff transport reminder for: " + booking.getBookingCode());
                }
            }
        }
    }
}