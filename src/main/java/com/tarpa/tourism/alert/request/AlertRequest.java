package com.tarpa.tourism.alert.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertRequest {

    // ==========================
    // ALERT CODE
    // ==========================

    private String alertCode;

    // ==========================
    // BOOKING
    // ==========================

    private Long bookingId;

    // ==========================
    // ALERT TYPE
    // ==========================

    private String type;

    // ==========================
    // TITLE
    // ==========================

    private String title;

    // ==========================
    // MESSAGE
    // ==========================

    private String message;

    // ==========================
    // SCHEDULE
    // ==========================

    private LocalDateTime scheduledAt;

    // ==========================
    // STATUS
    // ==========================

    private String status;

    // ==========================
    // ACTIVE
    // ==========================

    private Boolean active;
}