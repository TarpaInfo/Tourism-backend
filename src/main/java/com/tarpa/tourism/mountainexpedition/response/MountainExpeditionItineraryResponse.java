package com.tarpa.tourism.mountainexpedition.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MountainExpeditionItineraryResponse {

    private Long id;

    private Long mountainExpeditionId;

    private Integer dayNumber;

    private String title;

    private String description;

    private String startLocation;

    private String endLocation;

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