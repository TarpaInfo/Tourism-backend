package com.tarpa.tourism.trekking.repository;

import com.tarpa.tourism.trekking.entity.TrekkingItinerary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrekkingItineraryRepository
        extends JpaRepository<TrekkingItinerary, Long> {

    List<TrekkingItinerary> findByTrekkingIdOrderByDayNumberAsc(
            Long trekkingId
    );

    boolean existsByTrekkingIdAndDayNumber(
            Long trekkingId,
            Integer dayNumber
    );
}