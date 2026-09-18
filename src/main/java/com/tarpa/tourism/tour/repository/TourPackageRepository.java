package com.tarpa.tourism.tour.repository;

import com.tarpa.tourism.tour.entity.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TourPackageRepository
        extends JpaRepository<TourPackage, Long> {

    Optional<TourPackage> findByPackageCode(String packageCode);

    boolean existsByPackageCode(String packageCode);

    List<TourPackage> findByActiveTrue();

    List<TourPackage> findByPackageType(String packageType);

    List<TourPackage> findByDestination(String destination);
}