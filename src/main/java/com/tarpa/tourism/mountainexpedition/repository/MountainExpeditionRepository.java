package com.tarpa.tourism.mountainexpedition.repository;

import com.tarpa.tourism.mountainexpedition.entity.MountainExpedition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MountainExpeditionRepository
        extends JpaRepository<MountainExpedition, Long> {

    Optional<MountainExpedition> findByExpeditionCode(
            String expeditionCode
    );

    boolean existsByExpeditionCode(
            String expeditionCode
    );
}