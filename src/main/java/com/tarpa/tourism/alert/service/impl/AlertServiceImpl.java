package com.tarpa.tourism.alert.service.impl;

import com.tarpa.tourism.alert.entity.Alert;
import com.tarpa.tourism.alert.repository.AlertRepository;
import com.tarpa.tourism.alert.request.AlertRequest;
import com.tarpa.tourism.alert.response.AlertResponse;
import com.tarpa.tourism.alert.service.AlertService;
import com.tarpa.tourism.booking.entity.Booking;
import com.tarpa.tourism.booking.repository.BookingRepository;
import com.tarpa.tourism.exception.DuplicateResourceException;
import com.tarpa.tourism.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;
    private final BookingRepository bookingRepository;

    // ==========================
    // CREATE
    // ==========================

    @Override
    public AlertResponse createAlert(AlertRequest request) {

        if (alertRepository.existsByAlertCode(
                request.getAlertCode())) {

            throw new DuplicateResourceException(
                    "Alert code already exists: "
                            + request.getAlertCode()
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

        Alert alert = Alert.builder()
                .alertCode(request.getAlertCode())
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

        Alert saved = alertRepository.save(alert);

        return mapToResponse(saved);
    }

    // ==========================
    // GET ALL
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<AlertResponse> getAllAlerts() {

        return alertRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================
    // GET BY ID
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public AlertResponse getAlertById(Long id) {

        Alert alert = alertRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Alert not found with id: " + id
                        )
                );

        return mapToResponse(alert);
    }

    // ==========================
    // GET BY CODE
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public AlertResponse getAlertByCode(String alertCode) {

        Alert alert = alertRepository
                .findByAlertCode(alertCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Alert not found with code: "
                                        + alertCode
                        )
                );

        return mapToResponse(alert);
    }

    // ==========================
    // GET BY BOOKING ID
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<AlertResponse> getAlertsByBookingId(
            Long bookingId) {

        bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found with id: "
                                        + bookingId
                        )
                );

        return alertRepository
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
    public List<AlertResponse> getAlertsByStatus(
            String status) {

        return alertRepository
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
    public List<AlertResponse> getAlertsByType(
            String type) {

        return alertRepository
                .findByType(type)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================
    // UPDATE
    // ==========================

    @Override
    public AlertResponse updateAlert(
            Long id,
            AlertRequest request) {

        Alert alert = alertRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Alert not found with id: " + id
                        )
                );

        if (!alert.getAlertCode()
                .equals(request.getAlertCode())) {

            if (alertRepository.existsByAlertCode(
                    request.getAlertCode())) {

                throw new DuplicateResourceException(
                        "Alert code already exists: "
                                + request.getAlertCode()
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

        alert.setAlertCode(request.getAlertCode());
        alert.setBooking(booking);
        alert.setType(request.getType());
        alert.setTitle(request.getTitle());
        alert.setMessage(request.getMessage());
        alert.setScheduledAt(request.getScheduledAt());

        if (request.getStatus() != null) {
            alert.setStatus(request.getStatus());
        }

        if (request.getActive() != null) {
            alert.setActive(request.getActive());
        }

        Alert updated = alertRepository.save(alert);

        return mapToResponse(updated);
    }

    // ==========================
    // DELETE
    // ==========================

    @Override
    public void deleteAlert(Long id) {

        Alert alert = alertRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Alert not found with id: " + id
                        )
                );

        alertRepository.delete(alert);
    }

    // ==========================
    // MAPPER
    // ==========================

    private AlertResponse mapToResponse(Alert alert) {

        Booking booking = alert.getBooking();

        return AlertResponse.builder()
                .id(alert.getId())
                .alertCode(alert.getAlertCode())
                .bookingId(booking.getId())
                .bookingCode(booking.getBookingCode())
                .type(alert.getType())
                .title(alert.getTitle())
                .message(alert.getMessage())
                .scheduledAt(alert.getScheduledAt())
                .processedAt(alert.getProcessedAt())
                .status(alert.getStatus())
                .active(alert.getActive())
                .createdAt(alert.getCreatedAt())
                .updatedAt(alert.getUpdatedAt())
                .build();
    }
}