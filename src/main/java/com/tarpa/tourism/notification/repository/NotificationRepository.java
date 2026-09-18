package com.tarpa.tourism.notification.repository;

import com.tarpa.tourism.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    boolean existsByNotificationCode(String notificationCode);

    Optional<Notification> findByNotificationCode(
            String notificationCode
    );

    List<Notification> findByBookingId(Long bookingId);

    List<Notification> findByStatus(String status);

    List<Notification> findByType(String type);
}