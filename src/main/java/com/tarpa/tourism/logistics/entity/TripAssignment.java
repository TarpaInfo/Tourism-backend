package com.tarpa.tourism.logistics.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "trip_assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Column(nullable = false)
    private String pickupLocation;

    private String vehicleDetails; // e.g. "Toyota Land Cruiser - Ba 3 Cha 9012"

    @Column(length = 1000)
    private String operationalNotes; // e.g. "Pick up oxygen canisters at Lukla. Guest is vegetarian."

    @Builder.Default
    private boolean briefingSent = false;

    @Builder.Default
    private LocalDateTime assignedAt = LocalDateTime.now();
}