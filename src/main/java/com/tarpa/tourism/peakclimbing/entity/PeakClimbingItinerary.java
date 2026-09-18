package com.tarpa.tourism.peakclimbing.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "peak_climbing_itineraries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeakClimbingItinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================
    // PEAK CLIMBING REFERENCE
    // ==========================

    @Column(nullable = false)
    private Long peakClimbingId;

    // ==========================
    // DAY INFORMATION
    // ==========================

    @Column(nullable = false)
    private Integer dayNumber;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    // ==========================
    // LOCATION
    // ==========================

    private String startLocation;

    private String endLocation;

    // ==========================
    // CLIMBING INFORMATION
    // ==========================

    private Integer altitude;

    private String distance;

    private String walkingHours;

    private String climbingHours;

    private String climbingActivity;

    // ==========================
    // CAMP & ACCOMMODATION
    // ==========================

    private String camp;

    private String accommodation;

    private String meals;

    // ==========================
    // TECHNICAL DETAILS
    // ==========================

    private String difficulty;

    private String technicalRequirements;

    private String highlights;

    // ==========================
    // STATUS
    // ==========================

    @Column(nullable = false)
    private Boolean active = true;
}