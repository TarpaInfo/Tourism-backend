package com.tarpa.tourism.mountainexpedition.repository;

import com.tarpa.tourism.mountainexpedition.entity.MountainExpeditionItinerary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MountainExpeditionItineraryRepository
        extends JpaRepository<MountainExpeditionItinerary, Long> {

    List<MountainExpeditionItinerary>
    findByMountainExpeditionIdOrderByDayNumberAsc(
            Long mountainExpeditionId
    );

    boolean existsByMountainExpeditionIdAndDayNumber(
            Long mountainExpeditionId,
            Integer dayNumber
    );
}