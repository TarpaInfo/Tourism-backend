package com.tarpa.tourism.booking.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {

    private String bookingCode;

    private Long clientId;

    private Long tourPackageId;

    private LocalDate travelDate;

    private Integer numberOfTravelers;

    private BigDecimal totalAmount;

    private String currency;

    private String bookingStatus;

    private String paymentStatus;

    private String specialRequest;

    private Boolean active;

    private LocalDateTime arrivalDateTime;
    private LocalDateTime departureDateTime;
    private String flightDetails;

    private Boolean requiresAirportTransfer;
    private LocalDateTime transferTime;
    private String vehicleDetails;
}