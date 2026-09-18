package com.tarpa.tourism.hotel.scheduler;

import com.tarpa.tourism.alert.entity.Alert;
import com.tarpa.tourism.alert.repository.AlertRepository;
import com.tarpa.tourism.alert.request.AlertRequest;
import com.tarpa.tourism.alert.service.AlertService;
import com.tarpa.tourism.constant.AlertType;
import com.tarpa.tourism.hotel.entity.HotelBooking;
import com.tarpa.tourism.hotel.repository.HotelBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HotelAlertScheduler {

    private final HotelBookingRepository hotelBookingRepository;
    private final AlertRepository alertRepository;
    private final AlertService alertService;


    //Hotel Check-in Reminder (Day Before Check-in)

    @Scheduled(cron = "0 0 9 * * *")
//    @Scheduled(fixedRate = 30000)
    @Transactional
    public void createHotelCheckInReminders() {

        LocalDate tomorrow = LocalDate.now().plusDays(1);

        List<HotelBooking> hotelBookings =
                hotelBookingRepository.findByCheckInDate(tomorrow);

        for (HotelBooking hotelBooking : hotelBookings) {

            if (!"CONFIRMED".equalsIgnoreCase(
                    hotelBooking.getStatus())) {
                continue;
            }

            String alertCode =
                    "ALT-HTL-CIN-" +
                            hotelBooking.getHotelBookingCode();

            // Prevent duplicate alert
            if (alertRepository.existsByAlertCode(alertCode)) {
                continue;
            }

            AlertRequest alertRequest = AlertRequest.builder()
                    .alertCode(alertCode)
                    .bookingId(
                            hotelBooking.getBooking().getId()
                    )
                    .type(AlertType.HOTEL_CHECKIN_REMINDER)
                    .title("Hotel Check-in Reminder")
                    .message(
                            "Dear "
                                    + hotelBooking.getBooking()
                                    .getClient()
                                    .getFirstName()
                                    + ", this is a reminder that your hotel "
                                    + "check-in at "
                                    + hotelBooking.getHotelName()
                                    + " in "
                                    + hotelBooking.getCity()
                                    + " is scheduled for "
                                    + hotelBooking.getCheckInDate()
                                    + "."
                    )
                    .scheduledAt(LocalDateTime.now())
                    .status("PENDING")
                    .active(true)
                    .build();

            try {

                alertService.createAlert(alertRequest);

                System.out.println(
                        "Hotel check-in reminder created for: "
                                + hotelBooking.getHotelBookingCode()
                );

            } catch (Exception e) {

                System.out.println(
                        "Failed to create hotel check-in "
                                + "reminder for: "
                                + hotelBooking.getHotelBookingCode()
                );

                e.printStackTrace();
            }
        }
    }

//Hotel Check-in Alert (Day of Check-in)

    @Scheduled(cron = "0 0 10 * * *")
    @Transactional
    public void createHotelCheckInAlerts() {

        LocalDate today = LocalDate.now();
        List<HotelBooking> hotelBookings = hotelBookingRepository.findByCheckInDate(today);

        for (HotelBooking hotelBooking : hotelBookings) {
            if (!"CONFIRMED".equalsIgnoreCase(hotelBooking.getStatus())) continue;

            String alertCode = "ALT-HTL-IN-" + hotelBooking.getHotelBookingCode();
            if (alertRepository.existsByAlertCode(alertCode)) continue;

            AlertRequest alertRequest = AlertRequest.builder()
                    .alertCode(alertCode)
                    .bookingId(hotelBooking.getBooking().getId())
                    .type(AlertType.HOTEL_CHECKIN) // Ensure this exists in your AlertType
                    .title("Welcome to your Hotel!")
                    .message("Dear " + hotelBooking.getBooking().getClient().getFirstName()
                            + ", today is your check-in day at " + hotelBooking.getHotelName()
                            + ". We hope you have a wonderful stay in " + hotelBooking.getCity() + "!")
                    .scheduledAt(LocalDateTime.now())
                    .status("PENDING")
                    .active(true)
                    .build();

            try {
                alertService.createAlert(alertRequest);
                System.out.println("Hotel check-in alert created for: " + hotelBooking.getHotelBookingCode());
            } catch (Exception e) {
                System.out.println("Failed to create hotel check-in alert for: " + hotelBooking.getHotelBookingCode());
            }
        }
    }

    //Check-out Reminder (Day Before Check-out)

    @Scheduled(cron = "0 0 16 * * *")
    @Transactional
    public void createHotelCheckOutReminders() {

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<HotelBooking> hotelBookings = hotelBookingRepository.findByCheckOutDate(tomorrow);

        for (HotelBooking hotelBooking : hotelBookings) {
            if (!"CONFIRMED".equalsIgnoreCase(hotelBooking.getStatus())) continue;

            String alertCode = "ALT-HTL-COUT-" + hotelBooking.getHotelBookingCode();
            if (alertRepository.existsByAlertCode(alertCode)) continue;

            AlertRequest alertRequest = AlertRequest.builder()
                    .alertCode(alertCode)
                    .bookingId(hotelBooking.getBooking().getId())
                    .type(AlertType.HOTEL_CHECKOUT_REMINDER) // Ensure this exists in your AlertType
                    .title("Hotel Check-out Reminder")
                    .message("Dear " + hotelBooking.getBooking().getClient().getFirstName()
                            + ", this is a reminder that your check-out at " + hotelBooking.getHotelName()
                            + " is scheduled for tomorrow, " + hotelBooking.getCheckOutDate() + ". safe travels!")
                    .scheduledAt(LocalDateTime.now())
                    .status("PENDING")
                    .active(true)
                    .build();

            try {
                alertService.createAlert(alertRequest);
                System.out.println("Hotel check-out reminder created for: " + hotelBooking.getHotelBookingCode());
            } catch (Exception e) {
                System.out.println("Failed to create hotel check-out reminder for: " + hotelBooking.getHotelBookingCode());
            }
        }
    }

    //Hotel Check-in Alert (Day of Check-in)

    @Scheduled(cron = "0 0 11 * * *")
    @Transactional
    public void createHotelCheckOutAlerts() {

        LocalDate today = LocalDate.now();
        List<HotelBooking> hotelBookings = hotelBookingRepository.findByCheckOutDate(today);

        for (HotelBooking hotelBooking : hotelBookings) {
            if (!"CONFIRMED".equalsIgnoreCase(hotelBooking.getStatus())) continue;

            String alertCode = "ALT-HTL-OUT-" + hotelBooking.getHotelBookingCode();
            if (alertRepository.existsByAlertCode(alertCode)) continue;

            AlertRequest alertRequest = AlertRequest.builder()
                    .alertCode(alertCode)
                    .bookingId(hotelBooking.getBooking().getId())
                    .type(AlertType.HOTEL_CHECKOUT) // Ensure this exists in your AlertType
                    .title("Thank you for your stay!")
                    .message("Dear " + hotelBooking.getBooking().getClient().getFirstName()
                            + ", today is your check-out day at " + hotelBooking.getHotelName()
                            + ". We hope you enjoyed your stay in " + hotelBooking.getCity()
                            + ". Safe travels on your next adventure!")
                    .scheduledAt(LocalDateTime.now())
                    .status("PENDING")
                    .active(true)
                    .build();

            try {
                alertService.createAlert(alertRequest);
                System.out.println("Hotel check-out alert created for: " + hotelBooking.getHotelBookingCode());
            } catch (Exception e) {
                System.out.println("Failed to create hotel check-out alert for: " + hotelBooking.getHotelBookingCode());
            }
        }
    }
}