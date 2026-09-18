package com.tarpa.tourism.mountainexpedition.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "mountain_expeditions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MountainExpedition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================
    // BASIC INFORMATION
    // ==========================

    @Column(nullable = false, unique = true)
    private String expeditionCode;

    @Column(nullable = false)
    private String expeditionName;

    private String mountainName;

    private String region;

    private String destination;

    @Column(length = 1000)
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String description;

    // ==========================
    // MOUNTAIN INFORMATION
    // ==========================

    private Integer mountainHeight;

    private String expeditionGrade;

    private String technicalDifficulty;

    private String expeditionSeason;

    // ==========================
    // DURATION
    // ==========================

    private Integer durationDays;

    private Integer durationNights;

    // ==========================
    // LOCATIONS
    // ==========================

    private String startLocation;

    private String endLocation;

    private String baseCamp;

    private String advancedBaseCamp;

    // ==========================
    // GROUP INFORMATION
    // ==========================

    private Integer minGroupSize;

    private Integer maxGroupSize;

    // ==========================
    // EXPEDITION INFORMATION
    // ==========================

    private String climbingHours;

    private String expeditionDistance;

    private Integer maximumAltitude;

    private String routeDescription;

    // ==========================
    // PERMIT
    // ==========================

    private Boolean permitRequired = true;

    @Column(length = 1500)
    private String permitDetails;

    // ==========================
    // EQUIPMENT
    // ==========================

    @Column(columnDefinition = "TEXT")
    private String requiredEquipment;

    @Column(columnDefinition = "TEXT")
    private String expeditionEquipment;

    // ==========================
    // EXPEDITION TEAM
    // ==========================

    private Boolean expeditionGuideRequired = true;

    private Integer requiredClimbingGuides;

    private Integer requiredHighAltitudeWorkers;

    // ==========================
    // PRICE
    // ==========================

    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    private String currency;

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