package com.tarpa.tourism.mountainexpedition.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MountainExpeditionRequest {

    @NotBlank(message = "Expedition code is required")
    private String expeditionCode;

    @NotBlank(message = "Expedition name is required")
    private String expeditionName;

    @NotBlank(message = "Mountain name is required")
    private String mountainName;

    private String region;

    private String destination;

    @Size(max = 1000,
            message = "Short description cannot exceed 1000 characters")
    private String shortDescription;

    private String description;

    @NotNull(message = "Mountain height is required")
    @Positive(message = "Mountain height must be greater than zero")
    private Integer mountainHeight;

    private String expeditionGrade;

    private String technicalDifficulty;

    private String expeditionSeason;

    @NotNull(message = "Duration days is required")
    @Positive(message = "Duration days must be greater than zero")
    private Integer durationDays;

    @PositiveOrZero(message = "Duration nights cannot be negative")
    private Integer durationNights;

    private String startLocation;

    private String endLocation;

    private String baseCamp;

    private String advancedBaseCamp;

    @PositiveOrZero(message = "Minimum group size cannot be negative")
    private Integer minGroupSize;

    @PositiveOrZero(message = "Maximum group size cannot be negative")
    private Integer maxGroupSize;

    private String climbingHours;

    private String expeditionDistance;

    @PositiveOrZero(message = "Maximum altitude cannot be negative")
    private Integer maximumAltitude;

    private String routeDescription;

    private Boolean permitRequired;

    @Size(max = 1500,
            message = "Permit details cannot exceed 1500 characters")
    private String permitDetails;

    private String requiredEquipment;

    private String expeditionEquipment;

    private Boolean expeditionGuideRequired;

    @PositiveOrZero(message = "Required climbing guides cannot be negative")
    private Integer requiredClimbingGuides;

    @PositiveOrZero(
            message = "Required high altitude workers cannot be negative"
    )
    private Integer requiredHighAltitudeWorkers;

    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "Price cannot be negative"
    )
    private BigDecimal price;

    private String currency;

    private Boolean active;
}