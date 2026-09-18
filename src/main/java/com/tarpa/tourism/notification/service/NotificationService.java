package com.tarpa.tourism.notification.service;

import com.tarpa.tourism.notification.request.NotificationRequest;
import com.tarpa.tourism.notification.response.NotificationResponse;

import java.util.List;

public interface NotificationService {

    NotificationResponse createNotification(
            NotificationRequest request
    );

    List<NotificationResponse> getAllNotifications();

    NotificationResponse getNotificationById(Long id);

    NotificationResponse getNotificationByCode(
            String notificationCode
    );

    List<NotificationResponse> getNotificationsByBookingId(
            Long bookingId
    );

    List<NotificationResponse> getNotificationsByStatus(
            String status
    );

    List<NotificationResponse> getNotificationsByType(
            String type
    );

    NotificationResponse updateNotification(
            Long id,
            NotificationRequest request
    );

    void deleteNotification(Long id);
}