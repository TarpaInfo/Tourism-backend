package com.tarpa.tourism.tour.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourItineraryResponse {

    private Long id;

    private Long tourPackageId;

    private Integer dayNumber;

    private String title;

    private String description;

    private String startLocation;

    private String endLocation;

    private String accommodation;

    private String meals;

    private Integer altitude;

    private String distance;

    private Boolean active;
}