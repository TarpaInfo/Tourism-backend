package com.tarpa.tourism.tour.repository;

import com.tarpa.tourism.tour.entity.TourItinerary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourItineraryRepository
        extends JpaRepository<TourItinerary, Long> {

    List<TourItinerary> findByTourPackageId(Long tourPackageId);

    boolean existsByTourPackageIdAndDayNumber(
            Long tourPackageId,
            Integer dayNumber
    );
}