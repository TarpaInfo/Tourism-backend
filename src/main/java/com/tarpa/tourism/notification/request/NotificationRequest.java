package com.tarpa.tourism.notification.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {

    private String notificationCode;

    private Long bookingId;

    private String type;

    private String title;

    private String message;

    private LocalDateTime scheduledAt;

    private String status;

    private Boolean active;
}