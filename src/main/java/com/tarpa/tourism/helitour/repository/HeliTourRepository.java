package com.tarpa.tourism.helitour.repository;

import com.tarpa.tourism.helitour.entity.HeliTour;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeliTourRepository extends JpaRepository<HeliTour, Long> {

    Optional<HeliTour> findByHeliTourCode(String heliTourCode);

    boolean existsByHeliTourCode(String heliTourCode);
}