package com.tarpa.tourism.permit.dto;

import com.tarpa.tourism.permit.model.PermitStatus;
import com.tarpa.tourism.permit.model.PermitType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PermitRequestDto {
    private Long bookingId;
    private Long clientId;
    private PermitType permitType;
    private String governmentPermitNumber;
    private PermitStatus status;
    private BigDecimal feeInNpr;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String documentUrl;
    private String remarks;
}