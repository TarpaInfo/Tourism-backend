package com.tarpa.tourism.tour.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourPackageRequest {

    // ==========================
    // BASIC INFORMATION
    // ==========================

    @NotBlank(message = "Package code is required")
    private String packageCode;

    @NotBlank(message = "Package name is required")
    private String packageName;

    @NotBlank(message = "Package type is required")
    private String packageType;

    @NotBlank(message = "Destination is required")
    private String destination;


    // ==========================
    // DURATION
    // ==========================

    @NotNull(message = "Duration days is required")
    @Min(value = 1, message = "Duration must be at least 1 day")
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

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Price must be greater than 0")
    private BigDecimal price;

    @NotBlank(message = "Currency is required")
    private String currency;


    // ==========================
    // GROUP INFORMATION
    // ==========================

    private Integer minGroupSize;

    private Integer maxGroupSize;
}