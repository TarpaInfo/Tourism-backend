package com.tarpa.tourism.mountainexpedition.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MountainExpeditionResponse {

    private Long id;

    private String expeditionCode;

    private String expeditionName;

    private String mountainName;

    private String region;

    private String destination;

    private String shortDescription;

    private String description;

    private Integer mountainHeight;

    private String expeditionGrade;

    private String technicalDifficulty;

    private String expeditionSeason;

    private Integer durationDays;

    private Integer durationNights;

    private String startLocation;

    private String endLocation;

    private String baseCamp;

    private String advancedBaseCamp;

    private Integer minGroupSize;

    private Integer maxGroupSize;

    private String climbingHours;

    private String expeditionDistance;

    private Integer maximumAltitude;

    private String routeDescription;

    private Boolean permitRequired;

    private String permitDetails;

    private String requiredEquipment;

    private String expeditionEquipment;

    private Boolean expeditionGuideRequired;

    private Integer requiredClimbingGuides;

    private Integer requiredHighAltitudeWorkers;

    private BigDecimal price;

    private String currency;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}