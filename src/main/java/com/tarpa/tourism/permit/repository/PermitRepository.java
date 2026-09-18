package com.tarpa.tourism.permit.repository;

import com.tarpa.tourism.permit.entity.Permit;
import com.tarpa.tourism.permit.model.PermitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermitRepository extends JpaRepository<Permit, Long> {

    List<Permit> findByBookingId(Long bookingId);

    List<Permit> findByClientId(Long clientId);

    List<Permit> findByStatus(PermitStatus status);
}