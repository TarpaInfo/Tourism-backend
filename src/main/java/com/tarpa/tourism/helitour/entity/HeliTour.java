package com.tarpa.tourism.helitour.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "heli_tours")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeliTour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================
    // BASIC INFORMATION
    // ==========================

    @Column(nullable = false, unique = true)
    private String heliTourCode;

    @Column(nullable = false)
    private String heliTourName;

    private String region;

    private String destination;

    @Column(length = 1000)
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String description;

    // ==========================
    // TOUR INFORMATION
    // ==========================

    private Integer durationHours;

    private String flightType;

    private String helicopterType;

    private String departureLocation;

    private String landingLocation;

    private String returnLocation;

    // ==========================
    // CAPACITY
    // ==========================

    private Integer minPassengers;

    private Integer maxPassengers;

    // ==========================
    // ALTITUDE & DISTANCE
    // ==========================

    private Integer maximumAltitude;

    private String flightDistance;

    // ==========================
    // BEST SEASON
    // ==========================

    private String bestSeason;

    // ==========================
    // LANDING INFORMATION
    // ==========================

    private Boolean landingAllowed;

    private String landingDetails;

    // ==========================
    // SAFETY
    // ==========================

    private Boolean oxygenAvailable;

    private Boolean emergencySupportAvailable;

    private String safetyInformation;

    // ==========================
    // PRICE
    // ==========================

    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    private String currency;

    private Boolean pricePerPerson;

    // ==========================
    // STATUS
    // ==========================

    @Column(nullable = false)
    private Boolean active = true;

    // ==========================
    // TIMESTAMPS
    // ==========================

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ==========================
    // CREATE TIMESTAMP
    // ==========================

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    // ==========================
    // UPDATE TIMESTAMP
    // ==========================

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}