package com.tarpa.tourism.peakclimbing.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeakClimbingRequest {

    @NotBlank(message = "Peak code is required")
    private String peakCode;

    @NotBlank(message = "Peak name is required")
    private String peakName;

    private String region;

    private String destination;

    @Size(max = 1000,
            message = "Short description cannot exceed 1000 characters")
    private String shortDescription;

    private String description;

    @NotNull(message = "Peak height is required")
    @Positive(message = "Peak height must be greater than zero")
    private Integer peakHeight;

    private String climbingGrade;

    private String technicalDifficulty;

    private String climbingSeason;

    @NotNull(message = "Duration days is required")
    @Positive(message = "Duration days must be greater than zero")
    private Integer durationDays;

    @PositiveOrZero(message = "Duration nights cannot be negative")
    private Integer durationNights;

    private String startLocation;

    private String endLocation;

    private String baseCamp;

    private String highCamp;

    @PositiveOrZero(message = "Minimum group size cannot be negative")
    private Integer minGroupSize;

    @PositiveOrZero(message = "Maximum group size cannot be negative")
    private Integer maxGroupSize;

    private String climbingHours;

    private String distance;

    private Boolean permitRequired;

    @Size(max = 1000,
            message = "Permit details cannot exceed 1000 characters")
    private String permitDetails;

    private String requiredEquipment;

    private Boolean climbingGuideRequired;

    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "Price cannot be negative"
    )
    private BigDecimal price;

    private String currency;

    private Boolean active;
}