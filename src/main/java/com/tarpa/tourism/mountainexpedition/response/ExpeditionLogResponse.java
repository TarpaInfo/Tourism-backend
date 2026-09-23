package com.tarpa.tourism.mountainexpedition.response;

import com.tarpa.tourism.mountainexpedition.enums.ActivityCategory;
import com.tarpa.tourism.mountainexpedition.enums.IncidentSeverity;
import com.tarpa.tourism.mountainexpedition.enums.LogType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExpeditionLogResponse {
    private Long id;
    private Long bookingId;
    private String bookingCode;
    private String clientName;
    private String routeName;
    private ActivityCategory activityCategory;
    private LogType logType;
    private IncidentSeverity severity;
    private String locationName;
    private Integer altitudeMeters;
    private String reportNotes;
    private String reportedBy;
    private Boolean heliRescueRequested;
    private LocalDateTime timestamp;
}