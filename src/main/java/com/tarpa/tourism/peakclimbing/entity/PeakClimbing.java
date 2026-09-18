package com.tarpa.tourism.peakclimbing.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "peak_climbings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeakClimbing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic information
    @Column(nullable = false, unique = true)
    private String peakCode;

    @Column(nullable = false)
    private String peakName;

    private String region;

    private String destination;

    @Column(length = 1000)
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Peak information
    private Integer peakHeight;

    private String climbingGrade;

    private String technicalDifficulty;

    private String climbingSeason;

    // Duration
    private Integer durationDays;

    private Integer durationNights;

    // Locations
    private String startLocation;

    private String endLocation;

    private String baseCamp;

    private String highCamp;

    // Group information
    private Integer minGroupSize;

    private Integer maxGroupSize;

    // Climbing information
    private String climbingHours;

    private String distance;

    // Permit
    private Boolean permitRequired = true;

    @Column(length = 1000)
    private String permitDetails;

    // Equipment
    @Column(columnDefinition = "TEXT")
    private String requiredEquipment;

    // Guide
    private Boolean climbingGuideRequired = true;

    // Price
    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    private String currency;

    // Status
    @Column(nullable = false)
    private Boolean active = true;

    // Timestamps
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


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