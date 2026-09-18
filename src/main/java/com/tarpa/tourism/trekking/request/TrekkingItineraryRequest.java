package com.tarpa.tourism.trekking.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrekkingItineraryRequest {

    @NotNull(message = "Trekking ID is required")
    @Positive(message = "Trekking ID must be greater than zero")
    private Long trekkingId;

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

    private String accommodation;

    private String meals;

    private String difficulty;

    private String highlights;

    private Boolean active;
}