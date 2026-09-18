package com.tarpa.tourism.permit.entity;

import com.tarpa.tourism.permit.model.PermitStatus;
import com.tarpa.tourism.permit.model.PermitType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "permits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Enumerated(EnumType.STRING)
    @Column(name = "permit_type", nullable = false)
    private PermitType permitType;

    @Column(name = "government_permit_number")
    private String governmentPermitNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PermitStatus status = PermitStatus.DRAFT;

    @Column(precision = 10, scale = 2)
    private BigDecimal feeInNpr;

    private LocalDate issueDate;
    private LocalDate expiryDate;

    @Column(length = 500)
    private String documentUrl; // Scan copy / PDF download link

    @Column(length = 500)
    private String remarks;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}