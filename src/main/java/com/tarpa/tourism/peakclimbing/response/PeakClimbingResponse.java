package com.tarpa.tourism.peakclimbing.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeakClimbingResponse {

    private Long id;

    private String peakCode;

    private String peakName;

    private String region;

    private String destination;

    private String shortDescription;

    private String description;

    private Integer peakHeight;

    private String climbingGrade;

    private String technicalDifficulty;

    private String climbingSeason;

    private Integer durationDays;

    private Integer durationNights;

    private String startLocation;

    private String endLocation;

    private String baseCamp;

    private String highCamp;

    private Integer minGroupSize;

    private Integer maxGroupSize;

    private String climbingHours;

    private String distance;

    private Boolean permitRequired;

    private String permitDetails;

    private String requiredEquipment;

    private Boolean climbingGuideRequired;

    private BigDecimal price;

    private String currency;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}