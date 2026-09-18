package com.tarpa.tourism.peakclimbing.service.impl;

import com.tarpa.tourism.exception.DuplicateResourceException;
import com.tarpa.tourism.exception.ResourceNotFoundException;
import com.tarpa.tourism.peakclimbing.entity.PeakClimbing;
import com.tarpa.tourism.peakclimbing.repository.PeakClimbingRepository;
import com.tarpa.tourism.peakclimbing.request.PeakClimbingRequest;
import com.tarpa.tourism.peakclimbing.response.PeakClimbingResponse;
import com.tarpa.tourism.peakclimbing.service.PeakClimbingService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PeakClimbingServiceImpl
        implements PeakClimbingService {

    private final PeakClimbingRepository peakClimbingRepository;

    // ==========================
    // CREATE PEAK CLIMBING
    // ==========================

    @Override
    public PeakClimbingResponse createPeakClimbing(
            PeakClimbingRequest request) {

        if (peakClimbingRepository.existsByPeakCode(
                request.getPeakCode())) {

            throw new DuplicateResourceException(
                    "Peak code already exists: "
                            + request.getPeakCode()
            );
        }

        PeakClimbing peakClimbing = PeakClimbing.builder()
                .peakCode(request.getPeakCode())
                .peakName(request.getPeakName())
                .region(request.getRegion())
                .destination(request.getDestination())
                .shortDescription(request.getShortDescription())
                .description(request.getDescription())
                .peakHeight(request.getPeakHeight())
                .climbingGrade(request.getClimbingGrade())
                .technicalDifficulty(
                        request.getTechnicalDifficulty())
                .climbingSeason(request.getClimbingSeason())
                .durationDays(request.getDurationDays())
                .durationNights(request.getDurationNights())
                .startLocation(request.getStartLocation())
                .endLocation(request.getEndLocation())
                .baseCamp(request.getBaseCamp())
                .highCamp(request.getHighCamp())
                .minGroupSize(request.getMinGroupSize())
                .maxGroupSize(request.getMaxGroupSize())
                .climbingHours(request.getClimbingHours())
                .distance(request.getDistance())
                .permitRequired(
                        request.getPermitRequired() != null
                                ? request.getPermitRequired()
                                : true
                )
                .permitDetails(request.getPermitDetails())
                .requiredEquipment(
                        request.getRequiredEquipment())
                .climbingGuideRequired(
                        request.getClimbingGuideRequired() != null
                                ? request.getClimbingGuideRequired()
                                : true
                )
                .price(request.getPrice())
                .currency(request.getCurrency())
                .active(
                        request.getActive() != null
                                ? request.getActive()
                                : true
                )
                .build();

        PeakClimbing savedPeakClimbing =
                peakClimbingRepository.save(peakClimbing);

        return mapToResponse(savedPeakClimbing);
    }


    // ==========================
    // GET ALL
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<PeakClimbingResponse>
    getAllPeakClimbings() {

        return peakClimbingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // ==========================
    // GET BY ID
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public PeakClimbingResponse getPeakClimbingById(
            Long id) {

        PeakClimbing peakClimbing =
                peakClimbingRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Peak climbing not found with id: "
                                                + id
                                )
                        );

        return mapToResponse(peakClimbing);
    }


    // ==========================
    // UPDATE
    // ==========================

    @Override
    public PeakClimbingResponse updatePeakClimbing(
            Long id,
            PeakClimbingRequest request) {

        PeakClimbing peakClimbing =
                peakClimbingRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Peak climbing not found with id: "
                                                + id
                                )
                        );

        // Check duplicate peak code
        if (!peakClimbing.getPeakCode()
                .equals(request.getPeakCode())
                && peakClimbingRepository.existsByPeakCode(
                request.getPeakCode())) {

            throw new DuplicateResourceException(
                    "Peak code already exists: "
                            + request.getPeakCode()
            );
        }

        peakClimbing.setPeakCode(
                request.getPeakCode());

        peakClimbing.setPeakName(
                request.getPeakName());

        peakClimbing.setRegion(
                request.getRegion());

        peakClimbing.setDestination(
                request.getDestination());

        peakClimbing.setShortDescription(
                request.getShortDescription());

        peakClimbing.setDescription(
                request.getDescription());

        peakClimbing.setPeakHeight(
                request.getPeakHeight());

        peakClimbing.setClimbingGrade(
                request.getClimbingGrade());

        peakClimbing.setTechnicalDifficulty(
                request.getTechnicalDifficulty());

        peakClimbing.setClimbingSeason(
                request.getClimbingSeason());

        peakClimbing.setDurationDays(
                request.getDurationDays());

        peakClimbing.setDurationNights(
                request.getDurationNights());

        peakClimbing.setStartLocation(
                request.getStartLocation());

        peakClimbing.setEndLocation(
                request.getEndLocation());

        peakClimbing.setBaseCamp(
                request.getBaseCamp());

        peakClimbing.setHighCamp(
                request.getHighCamp());

        peakClimbing.setMinGroupSize(
                request.getMinGroupSize());

        peakClimbing.setMaxGroupSize(
                request.getMaxGroupSize());

        peakClimbing.setClimbingHours(
                request.getClimbingHours());

        peakClimbing.setDistance(
                request.getDistance());

        peakClimbing.setPermitRequired(
                request.getPermitRequired());

        peakClimbing.setPermitDetails(
                request.getPermitDetails());

        peakClimbing.setRequiredEquipment(
                request.getRequiredEquipment());

        peakClimbing.setClimbingGuideRequired(
                request.getClimbingGuideRequired());

        peakClimbing.setPrice(
                request.getPrice());

        peakClimbing.setCurrency(
                request.getCurrency());

        if (request.getActive() != null) {
            peakClimbing.setActive(
                    request.getActive());
        }

        PeakClimbing updatedPeakClimbing =
                peakClimbingRepository.save(peakClimbing);

        return mapToResponse(updatedPeakClimbing);
    }


    // ==========================
    // DELETE
    // ==========================

    @Override
    public void deletePeakClimbing(Long id) {

        PeakClimbing peakClimbing =
                peakClimbingRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Peak climbing not found with id: "
                                                + id
                                )
                        );

        peakClimbingRepository.delete(peakClimbing);
    }


    // ==========================
    // ENTITY → RESPONSE
    // ==========================

    private PeakClimbingResponse mapToResponse(
            PeakClimbing peakClimbing) {

        return PeakClimbingResponse.builder()
                .id(peakClimbing.getId())
                .peakCode(peakClimbing.getPeakCode())
                .peakName(peakClimbing.getPeakName())
                .region(peakClimbing.getRegion())
                .destination(peakClimbing.getDestination())
                .shortDescription(
                        peakClimbing.getShortDescription())
                .description(
                        peakClimbing.getDescription())
                .peakHeight(
                        peakClimbing.getPeakHeight())
                .climbingGrade(
                        peakClimbing.getClimbingGrade())
                .technicalDifficulty(
                        peakClimbing.getTechnicalDifficulty())
                .climbingSeason(
                        peakClimbing.getClimbingSeason())
                .durationDays(
                        peakClimbing.getDurationDays())
                .durationNights(
                        peakClimbing.getDurationNights())
                .startLocation(
                        peakClimbing.getStartLocation())
                .endLocation(
                        peakClimbing.getEndLocation())
                .baseCamp(
                        peakClimbing.getBaseCamp())
                .highCamp(
                        peakClimbing.getHighCamp())
                .minGroupSize(
                        peakClimbing.getMinGroupSize())
                .maxGroupSize(
                        peakClimbing.getMaxGroupSize())
                .climbingHours(
                        peakClimbing.getClimbingHours())
                .distance(
                        peakClimbing.getDistance())
                .permitRequired(
                        peakClimbing.getPermitRequired())
                .permitDetails(
                        peakClimbing.getPermitDetails())
                .requiredEquipment(
                        peakClimbing.getRequiredEquipment())
                .climbingGuideRequired(
                        peakClimbing.getClimbingGuideRequired())
                .price(
                        peakClimbing.getPrice())
                .currency(
                        peakClimbing.getCurrency())
                .active(
                        peakClimbing.getActive())
                .createdAt(
                        peakClimbing.getCreatedAt())
                .updatedAt(
                        peakClimbing.getUpdatedAt())
                .build();
    }
}