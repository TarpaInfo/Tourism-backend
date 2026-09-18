package com.tarpa.tourism.trekking.service.impl;

import com.tarpa.tourism.exception.DuplicateResourceException;
import com.tarpa.tourism.exception.ResourceNotFoundException;
import com.tarpa.tourism.trekking.entity.Trekking;
import com.tarpa.tourism.trekking.repository.TrekkingRepository;
import com.tarpa.tourism.trekking.request.TrekkingRequest;
import com.tarpa.tourism.trekking.response.TrekkingResponse;
import com.tarpa.tourism.trekking.service.TrekkingService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TrekkingServiceImpl implements TrekkingService {

    private final TrekkingRepository trekkingRepository;

    // ==========================
    // CREATE TREKKING
    // ==========================

    @Override
    public TrekkingResponse createTrekking(TrekkingRequest request) {

        if (trekkingRepository.existsByTrekkingCode(
                request.getTrekkingCode())) {

            throw new DuplicateResourceException(
                    "Trekking code already exists: "
                            + request.getTrekkingCode()
            );
        }

        Trekking trekking = Trekking.builder()
                .trekkingCode(request.getTrekkingCode())
                .trekkingName(request.getTrekkingName())
                .region(request.getRegion())
                .destination(request.getDestination())
                .shortDescription(request.getShortDescription())
                .description(request.getDescription())
                .durationDays(request.getDurationDays())
                .durationNights(request.getDurationNights())
                .difficulty(request.getDifficulty())
                .maxAltitude(request.getMaxAltitude())
                .distance(request.getDistance())
                .startLocation(request.getStartLocation())
                .endLocation(request.getEndLocation())
                .bestSeason(request.getBestSeason())
                .minGroupSize(request.getMinGroupSize())
                .maxGroupSize(request.getMaxGroupSize())
                .walkingHours(request.getWalkingHours())
                .price(request.getPrice())
                .currency(request.getCurrency())
                .permitRequired(request.getPermitRequired())
                .permitDetails(request.getPermitDetails())
                .guideRequired(request.getGuideRequired())
                .active(
                        request.getActive() != null
                                ? request.getActive()
                                : true
                )
                .build();

        Trekking savedTrekking =
                trekkingRepository.save(trekking);

        return mapToResponse(savedTrekking);
    }


    // ==========================
    // GET ALL TREKKINGS
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<TrekkingResponse> getAllTrekkings() {

        return trekkingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // ==========================
    // GET TREKKING BY ID
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public TrekkingResponse getTrekkingById(Long id) {

        Trekking trekking =
                trekkingRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Trekking not found with id: "
                                                + id
                                )
                        );

        return mapToResponse(trekking);
    }


    // ==========================
    // UPDATE TREKKING
    // ==========================

    @Override
    public TrekkingResponse updateTrekking(
            Long id,
            TrekkingRequest request) {

        Trekking trekking =
                trekkingRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Trekking not found with id: "
                                                + id
                                )
                        );

        // Check duplicate code only if code is changed
        if (!trekking.getTrekkingCode()
                .equals(request.getTrekkingCode())
                && trekkingRepository.existsByTrekkingCode(
                request.getTrekkingCode())) {

            throw new DuplicateResourceException(
                    "Trekking code already exists: "
                            + request.getTrekkingCode()
            );
        }

        trekking.setTrekkingCode(
                request.getTrekkingCode());

        trekking.setTrekkingName(
                request.getTrekkingName());

        trekking.setRegion(
                request.getRegion());

        trekking.setDestination(
                request.getDestination());

        trekking.setShortDescription(
                request.getShortDescription());

        trekking.setDescription(
                request.getDescription());

        trekking.setDurationDays(
                request.getDurationDays());

        trekking.setDurationNights(
                request.getDurationNights());

        trekking.setDifficulty(
                request.getDifficulty());

        trekking.setMaxAltitude(
                request.getMaxAltitude());

        trekking.setDistance(
                request.getDistance());

        trekking.setStartLocation(
                request.getStartLocation());

        trekking.setEndLocation(
                request.getEndLocation());

        trekking.setBestSeason(
                request.getBestSeason());

        trekking.setMinGroupSize(
                request.getMinGroupSize());

        trekking.setMaxGroupSize(
                request.getMaxGroupSize());

        trekking.setWalkingHours(
                request.getWalkingHours());

        trekking.setPrice(
                request.getPrice());

        trekking.setCurrency(
                request.getCurrency());

        trekking.setPermitRequired(
                request.getPermitRequired());

        trekking.setPermitDetails(
                request.getPermitDetails());

        trekking.setGuideRequired(
                request.getGuideRequired());

        if (request.getActive() != null) {
            trekking.setActive(
                    request.getActive());
        }

        Trekking updatedTrekking =
                trekkingRepository.save(trekking);

        return mapToResponse(updatedTrekking);
    }


    // ==========================
    // DELETE TREKKING
    // ==========================

    @Override
    public void deleteTrekking(Long id) {

        Trekking trekking =
                trekkingRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Trekking not found with id: "
                                                + id
                                )
                        );

        trekkingRepository.delete(trekking);
    }


    // ==========================
    // ENTITY → RESPONSE
    // ==========================

    private TrekkingResponse mapToResponse(
            Trekking trekking) {

        return TrekkingResponse.builder()
                .id(trekking.getId())
                .trekkingCode(trekking.getTrekkingCode())
                .trekkingName(trekking.getTrekkingName())
                .region(trekking.getRegion())
                .destination(trekking.getDestination())
                .shortDescription(
                        trekking.getShortDescription())
                .description(trekking.getDescription())
                .durationDays(
                        trekking.getDurationDays())
                .durationNights(
                        trekking.getDurationNights())
                .difficulty(trekking.getDifficulty())
                .maxAltitude(
                        trekking.getMaxAltitude())
                .distance(trekking.getDistance())
                .startLocation(
                        trekking.getStartLocation())
                .endLocation(
                        trekking.getEndLocation())
                .bestSeason(
                        trekking.getBestSeason())
                .minGroupSize(
                        trekking.getMinGroupSize())
                .maxGroupSize(
                        trekking.getMaxGroupSize())
                .walkingHours(
                        trekking.getWalkingHours())
                .price(trekking.getPrice())
                .currency(trekking.getCurrency())
                .permitRequired(
                        trekking.getPermitRequired())
                .permitDetails(
                        trekking.getPermitDetails())
                .guideRequired(
                        trekking.getGuideRequired())
                .active(trekking.getActive())
                .createdAt(trekking.getCreatedAt())
                .updatedAt(trekking.getUpdatedAt())
                .build();
    }
}