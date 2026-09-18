package com.tarpa.tourism.trekking.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrekkingRequest {

    @NotBlank(message = "Trekking code is required")
    private String trekkingCode;

    @NotBlank(message = "Trekking name is required")
    private String trekkingName;

    private String region;

    private String destination;

    @Size(max = 1000, message = "Short description cannot exceed 1000 characters")
    private String shortDescription;

    private String description;

    @NotNull(message = "Duration days is required")
    @Positive(message = "Duration days must be greater than zero")
    private Integer durationDays;

    @PositiveOrZero(message = "Duration nights cannot be negative")
    private Integer durationNights;

    private String difficulty;

    @Positive(message = "Maximum altitude must be greater than zero")
    private Integer maxAltitude;

    private String distance;

    private String startLocation;

    private String endLocation;

    private String bestSeason;

    @PositiveOrZero(message = "Minimum group size cannot be negative")
    private Integer minGroupSize;

    @PositiveOrZero(message = "Maximum group size cannot be negative")
    private Integer maxGroupSize;

    private String walkingHours;

    @DecimalMin(value = "0.0", inclusive = true,
            message = "Price cannot be negative")
    private BigDecimal price;

    private String currency;

    private Boolean permitRequired;

    @Size(max = 1000, message = "Permit details cannot exceed 1000 characters")
    private String permitDetails;

    private Boolean guideRequired;

    private Boolean active;
}