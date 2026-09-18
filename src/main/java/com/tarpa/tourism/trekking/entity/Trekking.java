package com.tarpa.tourism.trekking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trekkings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trekking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic trekking information
    @Column(nullable = false, unique = true)
    private String trekkingCode;

    @Column(nullable = false)
    private String trekkingName;

    private String region;

    private String destination;

    @Column(length = 1000)
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Trek duration
    private Integer durationDays;

    private Integer durationNights;

    // Difficulty
    private String difficulty;

    // Altitude
    private Integer maxAltitude;

    // Distance
    private String distance;

    // Starting and ending locations
    private String startLocation;

    private String endLocation;

    // Best season
    private String bestSeason;

    // Group information
    private Integer minGroupSize;

    private Integer maxGroupSize;

    // Trekking hours per day
    private String walkingHours;

    // Price
    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    private String currency;

    // Permit information
    private Boolean permitRequired = false;

    @Column(length = 1000)
    private String permitDetails;

    // Guide information
    private Boolean guideRequired = true;

    // Status
    @Column(nullable = false)
    private Boolean active = true;

    // Timestamps
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    // Automatically set timestamps
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}