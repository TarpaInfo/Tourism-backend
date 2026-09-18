package com.tarpa.tourism.trekking.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrekkingItineraryResponse {

    private Long id;

    private Long trekkingId;

    private Integer dayNumber;

    private String title;

    private String description;

    private String startLocation;

    private String endLocation;

    private Integer altitude;

    private String distance;

    private String walkingHours;

    private String accommodation;

    private String meals;

    private String difficulty;

    private String highlights;

    private Boolean active;
}