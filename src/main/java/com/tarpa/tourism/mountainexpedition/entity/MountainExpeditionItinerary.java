package com.tarpa.tourism.mountainexpedition.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mountain_expedition_itineraries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MountainExpeditionItinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================
    // EXPEDITION REFERENCE
    // ==========================

    @Column(nullable = false)
    private Long mountainExpeditionId;

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
    // ALTITUDE & DISTANCE
    // ==========================

    private Integer altitude;

    private String distance;

    private String walkingHours;

    private String expeditionHours;

    // ==========================
    // EXPEDITION ACTIVITY
    // ==========================

    private String expeditionActivity;

    private String acclimatization;

    private String climbingActivity;

    // ==========================
    // CAMP & ACCOMMODATION
    // ==========================

    private String camp;

    private String accommodation;

    private String meals;

    // ==========================
    // TECHNICAL INFORMATION
    // ==========================

    private String difficulty;

    private String technicalRequirements;

    private String highlights;

    private String expeditionNotes;

    // ==========================
    // STATUS
    // ==========================

    @Column(nullable = false)
    private Boolean active = true;
}