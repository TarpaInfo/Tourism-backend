package com.tarpa.tourism.tour.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tour_packages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

 //Basic Information

    @Column(nullable = false, unique = true)
    private String packageCode;

    @Column(nullable = false)
    private String packageName;

    private String packageType;

    private String destination;

   //Duration Information

    private Integer durationDays;

    private Integer durationNights;

    // ==========================
    // TREKKING INFORMATION
    // ==========================

    private String difficulty;

    private Integer maxAltitude;

    private String bestSeason;

    // ==========================
    // DESCRIPTION
    // ==========================

    @Column(columnDefinition = "TEXT")
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String description;

    // ==========================
    // LOCATION
    // ==========================

    private String startLocation;

    private String endLocation;

    // ==========================
    // PRICING
    // ==========================

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    private String currency;

    // ==========================
    // GROUP INFORMATION
    // ==========================

    private Integer minGroupSize;

    private Integer maxGroupSize;

    // ==========================
    // STATUS
    // ==========================

    @Builder.Default
    private Boolean active = true;

    // ==========================
    // TIMESTAMPS
    // ==========================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ==========================
    // PRE-PERSIST
    // ==========================

    @PrePersist
    protected void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (active == null) {
            active = true;
        }
    }

    // ==========================
    // PRE-UPDATE
    // ==========================

    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}