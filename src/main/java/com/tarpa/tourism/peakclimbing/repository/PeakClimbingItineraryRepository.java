package com.tarpa.tourism.peakclimbing.repository;

import com.tarpa.tourism.peakclimbing.entity.PeakClimbingItinerary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeakClimbingItineraryRepository
        extends JpaRepository<PeakClimbingItinerary, Long> {

    List<PeakClimbingItinerary> findByPeakClimbingIdOrderByDayNumberAsc(
            Long peakClimbingId
    );

    boolean existsByPeakClimbingIdAndDayNumber(
            Long peakClimbingId,
            Integer dayNumber
    );
}