package com.tarpa.tourism.hotel.entity;

import com.tarpa.tourism.booking.entity.Booking;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "hotel_bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================
    // HOTEL BOOKING CODE
    // ==========================

    @Column(nullable = false, unique = true)
    private String hotelBookingCode;

    // ==========================
    // MAIN TOUR BOOKING
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    // ==========================
    // HOTEL INFORMATION
    // ==========================

    @Column(nullable = false)
    private String hotelName;

    private String hotelAddress;

    private String city;

    private String country;

    // ==========================
    // STAY INFORMATION
    // ==========================

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    private Integer numberOfNights;

    // ==========================
    // ROOM INFORMATION
    // ==========================

    private String roomType;

    private Integer numberOfRooms;

    private Integer numberOfGuests;

    // ==========================
    // MEAL PLAN
    // ==========================

    private String mealPlan;

    // ==========================
    // PRICING
    // ==========================

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    private String currency;

    // ==========================
    // STATUS
    // ==========================

    @Builder.Default
    @Column(nullable = false)
    private String status = "PENDING";

    // ==========================
    // SPECIAL REQUEST
    // ==========================

    @Column(columnDefinition = "TEXT")
    private String specialRequest;

    // ==========================
    // ACTIVE
    // ==========================

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    // ==========================
    // TIMESTAMPS
    // ==========================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ==========================
    // PRE-PERSIST
    // ==========================

    @PrePersist
    protected void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (active == null) {
            active = true;
        }

        if (status == null) {
            status = "PENDING";
        }
    }

    // ==========================
    // PRE-UPDATE
    // ==========================

    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}