package com.tarpa.tourism.trekking.repository;

import com.tarpa.tourism.trekking.entity.Trekking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrekkingRepository extends JpaRepository<Trekking, Long> {

    Optional<Trekking> findByTrekkingCode(String trekkingCode);

    boolean existsByTrekkingCode(String trekkingCode);
}