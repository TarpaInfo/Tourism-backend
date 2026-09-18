package com.tarpa.tourism.logistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripAssignmentRequestDto {
    private Long bookingId;
    private Long staffId;
    private String pickupLocation;
    private String vehicleDetails;
    private String operationalNotes;
}