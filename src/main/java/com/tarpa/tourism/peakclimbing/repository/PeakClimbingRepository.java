package com.tarpa.tourism.peakclimbing.repository;

import com.tarpa.tourism.peakclimbing.entity.PeakClimbing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeakClimbingRepository
        extends JpaRepository<PeakClimbing, Long> {

    Optional<PeakClimbing> findByPeakCode(String peakCode);

    boolean existsByPeakCode(String peakCode);
}