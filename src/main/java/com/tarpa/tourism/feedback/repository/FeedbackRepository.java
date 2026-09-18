package com.tarpa.tourism.feedback.repository;

import com.tarpa.tourism.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Optional<Feedback> findByBookingId(Long bookingId);

    boolean existsByBookingId(Long bookingId);

    List<Feedback> findByApprovedTrueOrderByCreatedAtDesc();
}