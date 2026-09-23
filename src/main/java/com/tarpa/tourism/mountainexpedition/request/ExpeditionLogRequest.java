package com.tarpa.tourism.mountainexpedition.request;

import com.tarpa.tourism.mountainexpedition.enums.ActivityCategory;
import com.tarpa.tourism.mountainexpedition.enums.IncidentSeverity;
import com.tarpa.tourism.mountainexpedition.enums.LogType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExpeditionLogRequest {

    @NotNull(message = "Booking ID is required")
    private Long bookingId;

    private ActivityCategory activityCategory; // Can be provided or inferred from the Booking package

    @NotNull(message = "Log type is required")
    private LogType logType;

    @NotNull(message = "Severity is required")
    private IncidentSeverity severity;

    @NotBlank(message = "Location name is required")
    private String locationName;

    private Integer altitudeMeters;

    private String reportNotes;

    private String reportedBy;

    private Boolean heliRescueRequested = false;
}