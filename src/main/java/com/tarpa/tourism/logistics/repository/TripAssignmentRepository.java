package com.tarpa.tourism.logistics.repository;

import com.tarpa.tourism.logistics.entity.TripAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripAssignmentRepository extends JpaRepository<TripAssignment, Long> {

    List<TripAssignment> findByBookingId(Long bookingId);

    List<TripAssignment> findByStaffId(Long staffId);

    List<TripAssignment> findByBriefingSentFalse();
}