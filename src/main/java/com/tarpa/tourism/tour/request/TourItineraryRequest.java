package com.tarpa.tourism.tour.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourItineraryRequest {

    @NotNull(message = "Tour package ID is required")
    @Positive(message = "Tour package ID must be positive")
    private Long tourPackageId;

    @NotNull(message = "Day number is required")
    @Positive(message = "Day number must be positive")
    private Integer dayNumber;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private String startLocation;

    private String endLocation;

    private String accommodation;

    private String meals;

    private Integer altitude;

    private String distance;
}