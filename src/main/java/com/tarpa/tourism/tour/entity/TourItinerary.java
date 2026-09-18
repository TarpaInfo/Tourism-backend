package com.tarpa.tourism.tour.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tour_itineraries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourItinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which tour package this itinerary belongs to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_package_id", nullable = false)
    private TourPackage tourPackage;

    // Day number
    @Column(nullable = false)
    private Integer dayNumber;

    // Title of the day
    @Column(nullable = false)
    private String title;

    // Detailed description
    @Column(columnDefinition = "TEXT")
    private String description;

    // Starting location
    private String startLocation;

    // Ending location
    private String endLocation;

    // Optional accommodation information
    private String accommodation;

    // Optional meal information
    private String meals;

    // Optional altitude
    private Integer altitude;

    // Optional walking/drive distance
    private String distance;

    // Active/inactive itinerary
    @Column(nullable = false)
    private Boolean active = true;
}