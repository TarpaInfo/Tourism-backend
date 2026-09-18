package com.tarpa.tourism.tour.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourPackageResponse {

    private Long id;

    // ==========================
    // BASIC INFORMATION
    // ==========================

    private String packageCode;

    private String packageName;

    private String packageType;

    private String destination;

    // ==========================
    // DURATION
    // ==========================

    private Integer durationDays;

    private Integer durationNights;

    // ==========================
    // TREKKING INFORMATION
    // ==========================

    private String difficulty;

    private Integer maxAltitude;

    private String bestSeason;

    // ==========================
    // DESCRIPTION
    // ==========================

    private String shortDescription;

    private String description;

    // ==========================
    // LOCATION
    // ==========================

    private String startLocation;

    private String endLocation;

    // ==========================
    // PRICING
    // ==========================

    private BigDecimal price;

    private String currency;

    // ==========================
    // GROUP INFORMATION
    // ==========================

    private Integer minGroupSize;

    private Integer maxGroupSize;

    // ==========================
    // STATUS
    // ==========================

    private Boolean active;

    // ==========================
    // TIMESTAMPS
    // ==========================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}