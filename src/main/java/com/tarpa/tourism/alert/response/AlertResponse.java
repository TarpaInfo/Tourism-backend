package com.tarpa.tourism.alert.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertResponse {

    private Long id;

    private String alertCode;

    private Long bookingId;

    private String bookingCode;

    private String type;

    private String title;

    private String message;

    private LocalDateTime scheduledAt;

    private LocalDateTime processedAt;

    private String status;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}