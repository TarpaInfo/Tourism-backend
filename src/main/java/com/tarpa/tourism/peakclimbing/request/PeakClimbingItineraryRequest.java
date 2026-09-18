package com.tarpa.tourism.peakclimbing.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeakClimbingItineraryRequest {

    @NotNull(message = "Peak climbing ID is required")
    @Positive(message = "Peak climbing ID must be greater than zero")
    private Long peakClimbingId;

    @NotNull(message = "Day number is required")
    @Positive(message = "Day number must be greater than zero")
    private Integer dayNumber;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private String startLocation;

    private String endLocation;

    @PositiveOrZero(message = "Altitude cannot be negative")
    private Integer altitude;

    private String distance;

    private String walkingHours;

    private String climbingHours;

    private String climbingActivity;

    private String camp;

    private String accommodation;

    private String meals;

    private String difficulty;

    private String technicalRequirements;

    private String highlights;

    private Boolean active;
}