package com.tarpa.tourism.hotel.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelBookingResponse {

    private Long id;

    private String hotelBookingCode;

    private Long bookingId;

    private String bookingCode;

    private String hotelName;

    private String hotelAddress;

    private String city;

    private String country;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Integer numberOfNights;

    private String roomType;

    private Integer numberOfRooms;

    private Integer numberOfGuests;

    private String mealPlan;

    private BigDecimal totalAmount;

    private String currency;

    private String status;

    private String specialRequest;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}