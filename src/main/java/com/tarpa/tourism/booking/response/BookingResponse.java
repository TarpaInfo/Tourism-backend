package com.tarpa.tourism.booking.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long id;

    private String bookingCode;

    // Client information
    private Long clientId;
    private String clientName;

    // Tour package information
    private Long tourPackageId;
    private String packageCode;
    private String packageName;

    // Travel information
    private LocalDate travelDate;
    private Integer numberOfTravelers;

    // Pricing
    private BigDecimal totalAmount;
    private String currency;

    // Status
    private String bookingStatus;
    private String paymentStatus;

    // Additional information
    private String specialRequest;

    private Boolean active;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    // ARRIVAL & DEPARTURE (FLIGHTS)
    private LocalDateTime arrivalDateTime;
    private LocalDateTime departureDateTime;
    private String flightDetails;


    // TRANSPORTATION & TRANSFERS
    private Boolean requiresAirportTransfer;
    private LocalDateTime transferTime;
    private String vehicleDetails;
}