package com.tarpa.tourism.helitour.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeliTourResponse {

    private Long id;

    private String heliTourCode;

    private String heliTourName;

    private String region;

    private String destination;

    private String shortDescription;

    private String description;

    private Integer durationHours;

    private String flightType;

    private String helicopterType;

    private String departureLocation;

    private String landingLocation;

    private String returnLocation;

    private Integer minPassengers;

    private Integer maxPassengers;

    private Integer maximumAltitude;

    private String flightDistance;

    private String bestSeason;

    private Boolean landingAllowed;

    private String landingDetails;

    private Boolean oxygenAvailable;

    private Boolean emergencySupportAvailable;

    private String safetyInformation;

    private BigDecimal price;

    private String currency;

    private Boolean pricePerPerson;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}