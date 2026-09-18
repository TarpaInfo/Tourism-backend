package com.tarpa.tourism.notification.service.impl;

import com.tarpa.tourism.booking.entity.Booking;
import com.tarpa.tourism.booking.repository.BookingRepository;
import com.tarpa.tourism.exception.DuplicateResourceException;
import com.tarpa.tourism.exception.ResourceNotFoundException;
import com.tarpa.tourism.notification.entity.Notification;
import com.tarpa.tourism.notification.repository.NotificationRepository;
import com.tarpa.tourism.notification.request.NotificationRequest;
import com.tarpa.tourism.notification.response.NotificationResponse;
import com.tarpa.tourism.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final BookingRepository bookingRepository;

    // ==========================
    // CREATE
    // ==========================

    @Override
    public NotificationResponse createNotification(
            NotificationRequest request) {

        if (notificationRepository.existsByNotificationCode(
                request.getNotificationCode())) {

            throw new DuplicateResourceException(
                    "Notification code already exists: "
                            + request.getNotificationCode()
            );
        }

        Booking booking = bookingRepository.findById(
                request.getBookingId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Booking not found with id: "
                                + request.getBookingId()
                )
        );

        Notification notification = Notification.builder()
                .notificationCode(request.getNotificationCode())
                .booking(booking)
                .type(request.getType())
                .title(request.getTitle())
                .message(request.getMessage())
                .scheduledAt(request.getScheduledAt())
                .status(request.getStatus() != null
                        ? request.getStatus()
                        : "PENDING")
                .active(request.getActive() != null
                        ? request.getActive()
                        : true)
                .build();

        Notification saved =
                notificationRepository.save(notification);

        return mapToResponse(saved);
    }

    // ==========================
    // GET ALL
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllNotifications() {

        return notificationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================
    // GET BY ID
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public NotificationResponse getNotificationById(Long id) {

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification not found with id: "
                                                + id
                                )
                        );

        return mapToResponse(notification);
    }

    // ==========================
    // GET BY CODE
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public NotificationResponse getNotificationByCode(
            String notificationCode) {

        Notification notification =
                notificationRepository
                        .findByNotificationCode(notificationCode)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification not found with code: "
                                                + notificationCode
                                )
                        );

        return mapToResponse(notification);
    }

    // ==========================
    // GET BY BOOKING ID
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsByBookingId(
            Long bookingId) {

        bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found with id: "
                                        + bookingId
                        )
                );

        return notificationRepository
                .findByBookingId(bookingId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================
    // GET BY STATUS
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsByStatus(
            String status) {

        return notificationRepository
                .findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================
    // GET BY TYPE
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsByType(
            String type) {

        return notificationRepository
                .findByType(type)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================
    // UPDATE
    // ==========================

    @Override
    public NotificationResponse updateNotification(
            Long id,
            NotificationRequest request) {

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification not found with id: "
                                                + id
                                )
                        );

        if (!notification.getNotificationCode()
                .equals(request.getNotificationCode())) {

            if (notificationRepository.existsByNotificationCode(
                    request.getNotificationCode())) {

                throw new DuplicateResourceException(
                        "Notification code already exists: "
                                + request.getNotificationCode()
                );
            }
        }

        Booking booking = bookingRepository.findById(
                request.getBookingId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Booking not found with id: "
                                + request.getBookingId()
                )
        );

        notification.setNotificationCode(
                request.getNotificationCode()
        );

        notification.setBooking(booking);
        notification.setType(request.getType());
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setScheduledAt(request.getScheduledAt());

        if (request.getStatus() != null) {
            notification.setStatus(request.getStatus());

            // Automatically record when notification becomes SENT
            if ("SENT".equalsIgnoreCase(request.getStatus())
                    && notification.getSentAt() == null) {

                notification.setSentAt(LocalDateTime.now());
            }
        }

        if (request.getActive() != null) {
            notification.setActive(request.getActive());
        }

        Notification updated =
                notificationRepository.save(notification);

        return mapToResponse(updated);
    }

    // ==========================
    // DELETE
    // ==========================

    @Override
    public void deleteNotification(Long id) {

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification not found with id: "
                                                + id
                                )
                        );

        notificationRepository.delete(notification);
    }

    // ==========================
    // MAPPER
    // ==========================

    private NotificationResponse mapToResponse(
            Notification notification) {

        Booking booking = notification.getBooking();

        return NotificationResponse.builder()
                .id(notification.getId())
                .notificationCode(
                        notification.getNotificationCode()
                )
                .bookingId(booking.getId())
                .bookingCode(booking.getBookingCode())
                .type(notification.getType())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .scheduledAt(notification.getScheduledAt())
                .sentAt(notification.getSentAt())
                .status(notification.getStatus())
                .active(notification.getActive())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .build();
    }
}