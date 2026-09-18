package com.tarpa.tourism.trekking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trekking_itineraries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrekkingItinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================
    // TREKKING REFERENCE
    // ==========================

    @Column(nullable = false)
    private Long trekkingId;

    // ==========================
    // DAY INFORMATION
    // ==========================

    @Column(nullable = false)
    private Integer dayNumber;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    // ==========================
    // LOCATION
    // ==========================

    private String startLocation;

    private String endLocation;

    // ==========================
    // TREKKING INFORMATION
    // ==========================

    private Integer altitude;

    private String distance;

    private String walkingHours;

    // ==========================
    // ACCOMMODATION & MEALS
    // ==========================

    private String accommodation;

    private String meals;

    // ==========================
    // TREKKING DETAILS
    // ==========================

    private String difficulty;

    private String highlights;

    // ==========================
    // STATUS
    // ==========================

    @Column(nullable = false)
    private Boolean active = true;
}