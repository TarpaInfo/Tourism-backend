package com.tarpa.tourism.mountainexpedition.repository;

import com.tarpa.tourism.mountainexpedition.entity.ExpeditionLog;
import com.tarpa.tourism.mountainexpedition.enums.ActivityCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpeditionLogRepository extends JpaRepository<ExpeditionLog, Long> {

    // Unfiltered paginated logs
    Page<ExpeditionLog> findAllByOrderByTimestampDesc(Pageable pageable);

    // Filtered by Activity Category (e.g. only HELI_TOUR or only TOUR)
    Page<ExpeditionLog> findByActivityCategoryOrderByTimestampDesc(ActivityCategory category, Pageable pageable);

    // Logs for a specific booking
    List<ExpeditionLog> findByBookingIdOrderByTimestampDesc(Long bookingId);
}