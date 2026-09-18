package com.tarpa.tourism.tour.service.impl;

import com.tarpa.tourism.tour.entity.TourPackage;
import com.tarpa.tourism.tour.repository.TourPackageRepository;
import com.tarpa.tourism.tour.request.TourPackageRequest;
import com.tarpa.tourism.tour.response.TourPackageResponse;
import com.tarpa.tourism.tour.service.TourPackageService;
import com.tarpa.tourism.exception.DuplicateResourceException;
import com.tarpa.tourism.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TourPackageServiceImpl implements TourPackageService {

    private final TourPackageRepository tourPackageRepository;

    // =========================================================
    // CREATE PACKAGE
    // =========================================================

    @Override
    public TourPackageResponse createPackage(
            TourPackageRequest request) {

        // Check duplicate package code
        if (tourPackageRepository
                .existsByPackageCode(request.getPackageCode())) {

            throw new DuplicateResourceException(
                    "Package code already exists: "
                            + request.getPackageCode()
            );
        }

        TourPackage tourPackage = new TourPackage();

        mapRequestToEntity(request, tourPackage);

        tourPackage.setActive(true);

        TourPackage savedPackage =
                tourPackageRepository.save(tourPackage);

        return mapEntityToResponse(savedPackage);
    }

    // =========================================================
    // GET PACKAGE BY ID
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public TourPackageResponse getPackageById(Long id) {

        TourPackage tourPackage =
                tourPackageRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Tour package not found with id: " + id
                                )
                        );

        return mapEntityToResponse(tourPackage);
    }

    // =========================================================
    // GET ALL PACKAGES
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<TourPackageResponse> getAllPackages() {

        return tourPackageRepository.findAll()
                .stream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    // =========================================================
    // GET ACTIVE PACKAGES
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<TourPackageResponse> getActivePackages() {

        return tourPackageRepository.findByActiveTrue()
                .stream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    // =========================================================
    // GET PACKAGES BY TYPE
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<TourPackageResponse> getPackagesByType(
            String packageType) {

        return tourPackageRepository
                .findByPackageType(packageType)
                .stream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    // =========================================================
    // GET PACKAGES BY DESTINATION
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<TourPackageResponse> getPackagesByDestination(
            String destination) {

        return tourPackageRepository
                .findByDestination(destination)
                .stream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    // =========================================================
    // UPDATE PACKAGE
    // =========================================================

    @Override
    public TourPackageResponse updatePackage(
            Long id,
            TourPackageRequest request) {

        TourPackage tourPackage =
                tourPackageRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Tour package not found with id: "
                                                + id
                                )
                        );

        // Check package code belongs to another package
        tourPackageRepository
                .findByPackageCode(request.getPackageCode())
                .ifPresent(existingPackage -> {

                    if (!existingPackage.getId().equals(id)) {

                        throw new RuntimeException(
                                "Package code already belongs to another package"
                        );
                    }
                });

        mapRequestToEntity(request, tourPackage);

        TourPackage updatedPackage =
                tourPackageRepository.save(tourPackage);

        return mapEntityToResponse(updatedPackage);
    }

    // =========================================================
    // DELETE PACKAGE
    // =========================================================

    @Override
    public void deletePackage(Long id) {

        TourPackage tourPackage =
                tourPackageRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Tour package not found with id: "
                                                + id
                                )
                        );

        tourPackageRepository.delete(tourPackage);
    }

    // =========================================================
    // REQUEST → ENTITY
    // =========================================================

    private void mapRequestToEntity(
            TourPackageRequest request,
            TourPackage tourPackage) {

        tourPackage.setPackageCode(
                request.getPackageCode()
        );

        tourPackage.setPackageName(
                request.getPackageName()
        );

        tourPackage.setPackageType(
                request.getPackageType()
        );

        tourPackage.setDestination(
                request.getDestination()
        );

        tourPackage.setDurationDays(
                request.getDurationDays()
        );

        tourPackage.setDurationNights(
                request.getDurationNights()
        );

        tourPackage.setDifficulty(
                request.getDifficulty()
        );

        tourPackage.setMaxAltitude(
                request.getMaxAltitude()
        );

        tourPackage.setBestSeason(
                request.getBestSeason()
        );

        tourPackage.setShortDescription(
                request.getShortDescription()
        );

        tourPackage.setDescription(
                request.getDescription()
        );

        tourPackage.setStartLocation(
                request.getStartLocation()
        );

        tourPackage.setEndLocation(
                request.getEndLocation()
        );

        tourPackage.setPrice(
                request.getPrice()
        );

        tourPackage.setCurrency(
                request.getCurrency()
        );

        tourPackage.setMinGroupSize(
                request.getMinGroupSize()
        );

        tourPackage.setMaxGroupSize(
                request.getMaxGroupSize()
        );
    }

    // =========================================================
    // ENTITY → RESPONSE
    // =========================================================

    private TourPackageResponse mapEntityToResponse(
            TourPackage tourPackage) {

        return TourPackageResponse.builder()
                .id(tourPackage.getId())

                .packageCode(
                        tourPackage.getPackageCode()
                )

                .packageName(
                        tourPackage.getPackageName()
                )

                .packageType(
                        tourPackage.getPackageType()
                )

                .destination(
                        tourPackage.getDestination()
                )

                .durationDays(
                        tourPackage.getDurationDays()
                )

                .durationNights(
                        tourPackage.getDurationNights()
                )

                .difficulty(
                        tourPackage.getDifficulty()
                )

                .maxAltitude(
                        tourPackage.getMaxAltitude()
                )

                .bestSeason(
                        tourPackage.getBestSeason()
                )

                .shortDescription(
                        tourPackage.getShortDescription()
                )

                .description(
                        tourPackage.getDescription()
                )

                .startLocation(
                        tourPackage.getStartLocation()
                )

                .endLocation(
                        tourPackage.getEndLocation()
                )

                .price(
                        tourPackage.getPrice()
                )

                .currency(
                        tourPackage.getCurrency()
                )

                .minGroupSize(
                        tourPackage.getMinGroupSize()
                )

                .maxGroupSize(
                        tourPackage.getMaxGroupSize()
                )

                .active(
                        tourPackage.getActive()
                )

                .createdAt(
                        tourPackage.getCreatedAt()
                )

                .updatedAt(
                        tourPackage.getUpdatedAt()
                )

                .build();
    }
}