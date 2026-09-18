package com.tarpa.tourism.peakclimbing.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeakClimbingItineraryResponse {

    private Long id;

    private Long peakClimbingId;

    private Integer dayNumber;

    private String title;

    private String description;

    private String startLocation;

    private String endLocation;

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