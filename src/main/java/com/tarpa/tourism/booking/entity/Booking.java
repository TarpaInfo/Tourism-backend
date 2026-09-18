package com.tarpa.tourism.booking.entity;

import com.tarpa.tourism.entity.Client;
import com.tarpa.tourism.tour.entity.TourPackage;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //Booking Code

    @Column(nullable = false, unique = true)
    private String bookingCode;

   //Client Information

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

  //Tour Package Information

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_package_id", nullable = false)
    private TourPackage tourPackage;

  //Travel Information

    @Column(nullable = false)
    private LocalDate travelDate;

    @Column(nullable = false)
    private Integer numberOfTravelers;

  // ARRIVAL & DEPARTURE (FLIGHTS)

    private LocalDateTime arrivalDateTime;

    private LocalDateTime departureDateTime;

    @Column(columnDefinition = "TEXT")
    private String flightDetails;

   // TRANSPORTATION & TRANSFERS

    @Builder.Default
    private Boolean requiresAirportTransfer = false;

    private LocalDateTime transferTime;

    private String vehicleDetails;

    // Pricing

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    private String currency;

//Booking Status

    @Builder.Default
    @Column(nullable = false)
    private String bookingStatus = "PENDING";

    // Payment Status

    @Builder.Default
    @Column(nullable = false)
    private String paymentStatus = "UNPAID";

    // Special Requests

    @Column(columnDefinition = "TEXT")
    private String specialRequest;

   // Active Status

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    // Timestamps

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    //PRE-PERSIST

    @PrePersist
    protected void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (bookingStatus == null) {
            bookingStatus = "PENDING";
        }

        if (paymentStatus == null) {
            paymentStatus = "UNPAID";
        }

        if (active == null) {
            active = true;
        }
    }

  //PRE-UPDATE

    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}