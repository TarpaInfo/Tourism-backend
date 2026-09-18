package com.tarpa.tourism.mountainexpedition.request;

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
public class MountainExpeditionItineraryRequest {

    @NotNull(message = "Mountain expedition ID is required")
    @Positive(message = "Mountain expedition ID must be greater than zero")
    private Long mountainExpeditionId;

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

    private String expeditionHours;

    private String expeditionActivity;

    private String acclimatization;

    private String climbingActivity;

    private String camp;

    private String accommodation;

    private String meals;

    private String difficulty;

    private String technicalRequirements;

    private String highlights;

    private String expeditionNotes;

    private Boolean active;
}