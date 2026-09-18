package com.tarpa.tourism.notification.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {

    private Long id;

    private String notificationCode;

    private Long bookingId;

    private String bookingCode;

    private String type;

    private String title;

    private String message;

    private LocalDateTime scheduledAt;

    private LocalDateTime sentAt;

    private String status;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}