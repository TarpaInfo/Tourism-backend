package com.tarpa.tourism.mountainexpedition.service.impl;

import com.tarpa.tourism.exception.DuplicateResourceException;
import com.tarpa.tourism.exception.ResourceNotFoundException;
import com.tarpa.tourism.mountainexpedition.entity.MountainExpedition;
import com.tarpa.tourism.mountainexpedition.repository.MountainExpeditionRepository;
import com.tarpa.tourism.mountainexpedition.request.MountainExpeditionRequest;
import com.tarpa.tourism.mountainexpedition.response.MountainExpeditionResponse;
import com.tarpa.tourism.mountainexpedition.service.MountainExpeditionService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MountainExpeditionServiceImpl
        implements MountainExpeditionService {

    private final MountainExpeditionRepository
            mountainExpeditionRepository;

    // ==========================
    // CREATE
    // ==========================

    @Override
    public MountainExpeditionResponse createMountainExpedition(
            MountainExpeditionRequest request) {

        if (mountainExpeditionRepository.existsByExpeditionCode(
                request.getExpeditionCode())) {

            throw new DuplicateResourceException(
                    "Expedition code already exists: "
                            + request.getExpeditionCode()
            );
        }

        MountainExpedition expedition =
                MountainExpedition.builder()
                        .expeditionCode(
                                request.getExpeditionCode())
                        .expeditionName(
                                request.getExpeditionName())
                        .mountainName(
                                request.getMountainName())
                        .region(request.getRegion())
                        .destination(request.getDestination())
                        .shortDescription(
                                request.getShortDescription())
                        .description(
                                request.getDescription())
                        .mountainHeight(
                                request.getMountainHeight())
                        .expeditionGrade(
                                request.getExpeditionGrade())
                        .technicalDifficulty(
                                request.getTechnicalDifficulty())
                        .expeditionSeason(
                                request.getExpeditionSeason())
                        .durationDays(
                                request.getDurationDays())
                        .durationNights(
                                request.getDurationNights())
                        .startLocation(
                                request.getStartLocation())
                        .endLocation(
                                request.getEndLocation())
                        .baseCamp(
                                request.getBaseCamp())
                        .advancedBaseCamp(
                                request.getAdvancedBaseCamp())
                        .minGroupSize(
                                request.getMinGroupSize())
                        .maxGroupSize(
                                request.getMaxGroupSize())
                        .climbingHours(
                                request.getClimbingHours())
                        .expeditionDistance(
                                request.getExpeditionDistance())
                        .maximumAltitude(
                                request.getMaximumAltitude())
                        .routeDescription(
                                request.getRouteDescription())
                        .permitRequired(
                                request.getPermitRequired() != null
                                        ? request.getPermitRequired()
                                        : true)
                        .permitDetails(
                                request.getPermitDetails())
                        .requiredEquipment(
                                request.getRequiredEquipment())
                        .expeditionEquipment(
                                request.getExpeditionEquipment())
                        .expeditionGuideRequired(
                                request.getExpeditionGuideRequired() != null
                                        ? request.getExpeditionGuideRequired()
                                        : true)
                        .requiredClimbingGuides(
                                request.getRequiredClimbingGuides())
                        .requiredHighAltitudeWorkers(
                                request.getRequiredHighAltitudeWorkers())
                        .price(request.getPrice())
                        .currency(request.getCurrency())
                        .active(
                                request.getActive() != null
                                        ? request.getActive()
                                        : true)
                        .build();

        MountainExpedition saved =
                mountainExpeditionRepository.save(expedition);

        return mapToResponse(saved);
    }

    // ==========================
    // GET ALL
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<MountainExpeditionResponse>
    getAllMountainExpeditions() {

        return mountainExpeditionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================
    // GET BY ID
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public MountainExpeditionResponse getMountainExpeditionById(
            Long id) {

        MountainExpedition expedition =
                mountainExpeditionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Mountain expedition not found with id: "
                                                + id
                                )
                        );

        return mapToResponse(expedition);
    }

    // ==========================
    // UPDATE
    // ==========================

    @Override
    public MountainExpeditionResponse updateMountainExpedition(
            Long id,
            MountainExpeditionRequest request) {

        MountainExpedition expedition =
                mountainExpeditionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Mountain expedition not found with id: "
                                                + id
                                )
                        );

        if (!expedition.getExpeditionCode()
                .equals(request.getExpeditionCode())
                && mountainExpeditionRepository.existsByExpeditionCode(
                request.getExpeditionCode())) {

            throw new DuplicateResourceException(
                    "Expedition code already exists: "
                            + request.getExpeditionCode()
            );
        }

        expedition.setExpeditionCode(
                request.getExpeditionCode());

        expedition.setExpeditionName(
                request.getExpeditionName());

        expedition.setMountainName(
                request.getMountainName());

        expedition.setRegion(
                request.getRegion());

        expedition.setDestination(
                request.getDestination());

        expedition.setShortDescription(
                request.getShortDescription());

        expedition.setDescription(
                request.getDescription());

        expedition.setMountainHeight(
                request.getMountainHeight());

        expedition.setExpeditionGrade(
                request.getExpeditionGrade());

        expedition.setTechnicalDifficulty(
                request.getTechnicalDifficulty());

        expedition.setExpeditionSeason(
                request.getExpeditionSeason());

        expedition.setDurationDays(
                request.getDurationDays());

        expedition.setDurationNights(
                request.getDurationNights());

        expedition.setStartLocation(
                request.getStartLocation());

        expedition.setEndLocation(
                request.getEndLocation());

        expedition.setBaseCamp(
                request.getBaseCamp());

        expedition.setAdvancedBaseCamp(
                request.getAdvancedBaseCamp());

        expedition.setMinGroupSize(
                request.getMinGroupSize());

        expedition.setMaxGroupSize(
                request.getMaxGroupSize());

        expedition.setClimbingHours(
                request.getClimbingHours());

        expedition.setExpeditionDistance(
                request.getExpeditionDistance());

        expedition.setMaximumAltitude(
                request.getMaximumAltitude());

        expedition.setRouteDescription(
                request.getRouteDescription());

        expedition.setPermitRequired(
                request.getPermitRequired());

        expedition.setPermitDetails(
                request.getPermitDetails());

        expedition.setRequiredEquipment(
                request.getRequiredEquipment());

        expedition.setExpeditionEquipment(
                request.getExpeditionEquipment());

        expedition.setExpeditionGuideRequired(
                request.getExpeditionGuideRequired());

        expedition.setRequiredClimbingGuides(
                request.getRequiredClimbingGuides());

        expedition.setRequiredHighAltitudeWorkers(
                request.getRequiredHighAltitudeWorkers());

        expedition.setPrice(
                request.getPrice());

        expedition.setCurrency(
                request.getCurrency());

        if (request.getActive() != null) {
            expedition.setActive(
                    request.getActive());
        }

        MountainExpedition updated =
                mountainExpeditionRepository.save(expedition);

        return mapToResponse(updated);
    }

    // ==========================
    // DELETE
    // ==========================

    @Override
    public void deleteMountainExpedition(Long id) {

        MountainExpedition expedition =
                mountainExpeditionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Mountain expedition not found with id: "
                                                + id
                                )
                        );

        mountainExpeditionRepository.delete(expedition);
    }

    // ==========================
    // ENTITY → RESPONSE
    // ==========================

    private MountainExpeditionResponse mapToResponse(
            MountainExpedition expedition) {

        return MountainExpeditionResponse.builder()
                .id(expedition.getId())
                .expeditionCode(
                        expedition.getExpeditionCode())
                .expeditionName(
                        expedition.getExpeditionName())
                .mountainName(
                        expedition.getMountainName())
                .region(
                        expedition.getRegion())
                .destination(
                        expedition.getDestination())
                .shortDescription(
                        expedition.getShortDescription())
                .description(
                        expedition.getDescription())
                .mountainHeight(
                        expedition.getMountainHeight())
                .expeditionGrade(
                        expedition.getExpeditionGrade())
                .technicalDifficulty(
                        expedition.getTechnicalDifficulty())
                .expeditionSeason(
                        expedition.getExpeditionSeason())
                .durationDays(
                        expedition.getDurationDays())
                .durationNights(
                        expedition.getDurationNights())
                .startLocation(
                        expedition.getStartLocation())
                .endLocation(
                        expedition.getEndLocation())
                .baseCamp(
                        expedition.getBaseCamp())
                .advancedBaseCamp(
                        expedition.getAdvancedBaseCamp())
                .minGroupSize(
                        expedition.getMinGroupSize())
                .maxGroupSize(
                        expedition.getMaxGroupSize())
                .climbingHours(
                        expedition.getClimbingHours())
                .expeditionDistance(
                        expedition.getExpeditionDistance())
                .maximumAltitude(
                        expedition.getMaximumAltitude())
                .routeDescription(
                        expedition.getRouteDescription())
                .permitRequired(
                        expedition.getPermitRequired())
                .permitDetails(
                        expedition.getPermitDetails())
                .requiredEquipment(
                        expedition.getRequiredEquipment())
                .expeditionEquipment(
                        expedition.getExpeditionEquipment())
                .expeditionGuideRequired(
                        expedition.getExpeditionGuideRequired())
                .requiredClimbingGuides(
                        expedition.getRequiredClimbingGuides())
                .requiredHighAltitudeWorkers(
                        expedition.getRequiredHighAltitudeWorkers())
                .price(
                        expedition.getPrice())
                .currency(
                        expedition.getCurrency())
                .active(
                        expedition.getActive())
                .createdAt(
                        expedition.getCreatedAt())
                .updatedAt(
                        expedition.getUpdatedAt())
                .build();
    }
}