package com.tarpa.tourism.helitour.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeliTourRequest {

    @NotBlank(message = "Heli tour code is required")
    private String heliTourCode;

    @NotBlank(message = "Heli tour name is required")
    private String heliTourName;

    private String region;

    private String destination;

    @Size(max = 1000,
            message = "Short description cannot exceed 1000 characters")
    private String shortDescription;

    private String description;

    @NotNull(message = "Duration hours is required")
    @Positive(message = "Duration hours must be greater than zero")
    private Integer durationHours;

    private String flightType;

    private String helicopterType;

    private String departureLocation;

    private String landingLocation;

    private String returnLocation;

    @PositiveOrZero(message = "Minimum passengers cannot be negative")
    private Integer minPassengers;

    @PositiveOrZero(message = "Maximum passengers cannot be negative")
    private Integer maxPassengers;

    @PositiveOrZero(message = "Maximum altitude cannot be negative")
    private Integer maximumAltitude;

    private String flightDistance;

    private String bestSeason;

    private Boolean landingAllowed;

    @Size(max = 1500,
            message = "Landing details cannot exceed 1500 characters")
    private String landingDetails;

    private Boolean oxygenAvailable;

    private Boolean emergencySupportAvailable;

    private String safetyInformation;

    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "Price cannot be negative"
    )
    private BigDecimal price;

    private String currency;

    private Boolean pricePerPerson;

    private Boolean active;
}