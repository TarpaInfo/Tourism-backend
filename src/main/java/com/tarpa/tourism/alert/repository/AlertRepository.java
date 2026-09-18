package com.tarpa.tourism.alert.repository;

import com.tarpa.tourism.alert.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    boolean existsByAlertCode(String alertCode);

    Optional<Alert> findByAlertCode(String alertCode);

    List<Alert> findByBookingId(Long bookingId);

    List<Alert> findByStatus(String status);

    List<Alert> findByType(String type);

    List<Alert> findByStatusAndScheduledAtLessThanEqual(
            String status,
            LocalDateTime scheduledAt
    );
}