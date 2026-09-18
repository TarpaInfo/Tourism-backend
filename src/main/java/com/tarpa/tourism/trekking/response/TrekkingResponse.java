package com.tarpa.tourism.trekking.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrekkingResponse {

    private Long id;

    private String trekkingCode;

    private String trekkingName;

    private String region;

    private String destination;

    private String shortDescription;

    private String description;

    private Integer durationDays;

    private Integer durationNights;

    private String difficulty;

    private Integer maxAltitude;

    private String distance;

    private String startLocation;

    private String endLocation;

    private String bestSeason;

    private Integer minGroupSize;

    private Integer maxGroupSize;

    private String walkingHours;

    private BigDecimal price;

    private String currency;

    private Boolean permitRequired;

    private String permitDetails;

    private Boolean guideRequired;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}