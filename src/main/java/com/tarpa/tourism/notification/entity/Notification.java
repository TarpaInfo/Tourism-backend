package com.tarpa.tourism.notification.entity;

import com.tarpa.tourism.booking.entity.Booking;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================
    // NOTIFICATION CODE
    // ==========================

    @Column(nullable = false, unique = true)
    private String notificationCode;

    // ==========================
    // BOOKING
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    // ==========================
    // NOTIFICATION TYPE
    // ==========================

    @Column(nullable = false)
    private String type;

    // ==========================
    // TITLE
    // ==========================

    @Column(nullable = false)
    private String title;

    // ==========================
    // MESSAGE
    // ==========================

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    // ==========================
    // SCHEDULE
    // ==========================

    private LocalDateTime scheduledAt;

    // ==========================
    // SENT TIME
    // ==========================

    private LocalDateTime sentAt;

    // ==========================
    // STATUS
    // ==========================

    @Builder.Default
    @Column(nullable = false)
    private String status = "PENDING";

    // ==========================
    // ACTIVE
    // ==========================

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    // ==========================
    // TIMESTAMPS
    // ==========================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ==========================
    // CREATE
    // ==========================

    @PrePersist
    protected void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (status == null) {
            status = "PENDING";
        }

        if (active == null) {
            active = true;
        }
    }

    // ==========================
    // UPDATE
    // ==========================

    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}