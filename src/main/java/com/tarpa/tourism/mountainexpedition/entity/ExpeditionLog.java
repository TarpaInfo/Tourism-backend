package com.tarpa.tourism.mountainexpedition.entity;

import com.tarpa.tourism.booking.entity.Booking;
import com.tarpa.tourism.mountainexpedition.enums.ActivityCategory;
import com.tarpa.tourism.mountainexpedition.enums.IncidentSeverity;
import com.tarpa.tourism.mountainexpedition.enums.LogType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "expedition_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpeditionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ActivityCategory activityCategory; // TREKKING, PEAK_CLIMBING, EXPEDITION, TOUR, HELI_TOUR

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private LogType logType; // WAYPOINT_CHECKIN, MEDICAL_INCIDENT, WEATHER_HOLD, EMERGENCY_EVAC

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private IncidentSeverity severity; // ROUTINE, MILD, MODERATE, CRITICAL_EMERGENCY

    @Column(nullable = false)
    private String locationName; // e.g., "Kala Patthar", "Pokhara Airport", "Camp IV"

    private Integer altitudeMeters; // Optional: relevant for trekking/climbing, null for low-altitude tours

    @Column(columnDefinition = "TEXT")
    private String reportNotes;

    private String reportedBy; // e.g., "Lead Guide Pasang Sherpa" or "Pilot Capt. Gurung"

    private Boolean heliRescueRequested;

    @CreationTimestamp
    private LocalDateTime timestamp;
}