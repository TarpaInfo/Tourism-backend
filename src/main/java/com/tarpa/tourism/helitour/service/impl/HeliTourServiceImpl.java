package com.tarpa.tourism.helitour.service.impl;

import com.tarpa.tourism.exception.DuplicateResourceException;
import com.tarpa.tourism.exception.ResourceNotFoundException;
import com.tarpa.tourism.helitour.entity.HeliTour;
import com.tarpa.tourism.helitour.repository.HeliTourRepository;
import com.tarpa.tourism.helitour.request.HeliTourRequest;
import com.tarpa.tourism.helitour.response.HeliTourResponse;
import com.tarpa.tourism.helitour.service.HeliTourService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HeliTourServiceImpl implements HeliTourService {

    private final HeliTourRepository heliTourRepository;

    // ==========================
    // CREATE
    // ==========================

    @Override
    public HeliTourResponse createHeliTour(
            HeliTourRequest request) {

        if (heliTourRepository.existsByHeliTourCode(
                request.getHeliTourCode())) {

            throw new DuplicateResourceException(
                    "Heli tour code already exists: "
                            + request.getHeliTourCode()
            );
        }

        HeliTour heliTour = HeliTour.builder()
                .heliTourCode(request.getHeliTourCode())
                .heliTourName(request.getHeliTourName())
                .region(request.getRegion())
                .destination(request.getDestination())
                .shortDescription(request.getShortDescription())
                .description(request.getDescription())
                .durationHours(request.getDurationHours())
                .flightType(request.getFlightType())
                .helicopterType(request.getHelicopterType())
                .departureLocation(request.getDepartureLocation())
                .landingLocation(request.getLandingLocation())
                .returnLocation(request.getReturnLocation())
                .minPassengers(request.getMinPassengers())
                .maxPassengers(request.getMaxPassengers())
                .maximumAltitude(request.getMaximumAltitude())
                .flightDistance(request.getFlightDistance())
                .bestSeason(request.getBestSeason())
                .landingAllowed(request.getLandingAllowed())
                .landingDetails(request.getLandingDetails())
                .oxygenAvailable(request.getOxygenAvailable())
                .emergencySupportAvailable(
                        request.getEmergencySupportAvailable())
                .safetyInformation(request.getSafetyInformation())
                .price(request.getPrice())
                .currency(request.getCurrency())
                .pricePerPerson(request.getPricePerPerson())
                .active(request.getActive() != null
                        ? request.getActive()
                        : true)
                .build();

        HeliTour saved =
                heliTourRepository.save(heliTour);

        return mapToResponse(saved);
    }

    // ==========================
    // GET ALL
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<HeliTourResponse> getAllHeliTours() {

        return heliTourRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================
    // GET BY ID
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public HeliTourResponse getHeliTourById(Long id) {

        HeliTour heliTour =
                heliTourRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Heli tour not found with id: "
                                                + id
                                )
                        );

        return mapToResponse(heliTour);
    }

    // ==========================
    // UPDATE
    // ==========================

    @Override
    public HeliTourResponse updateHeliTour(
            Long id,
            HeliTourRequest request) {

        HeliTour heliTour =
                heliTourRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Heli tour not found with id: "
                                                + id
                                )
                        );

        if (!heliTour.getHeliTourCode()
                .equals(request.getHeliTourCode())
                && heliTourRepository.existsByHeliTourCode(
                request.getHeliTourCode())) {

            throw new DuplicateResourceException(
                    "Heli tour code already exists: "
                            + request.getHeliTourCode()
            );
        }

        heliTour.setHeliTourCode(
                request.getHeliTourCode());

        heliTour.setHeliTourName(
                request.getHeliTourName());

        heliTour.setRegion(
                request.getRegion());

        heliTour.setDestination(
                request.getDestination());

        heliTour.setShortDescription(
                request.getShortDescription());

        heliTour.setDescription(
                request.getDescription());

        heliTour.setDurationHours(
                request.getDurationHours());

        heliTour.setFlightType(
                request.getFlightType());

        heliTour.setHelicopterType(
                request.getHelicopterType());

        heliTour.setDepartureLocation(
                request.getDepartureLocation());

        heliTour.setLandingLocation(
                request.getLandingLocation());

        heliTour.setReturnLocation(
                request.getReturnLocation());

        heliTour.setMinPassengers(
                request.getMinPassengers());

        heliTour.setMaxPassengers(
                request.getMaxPassengers());

        heliTour.setMaximumAltitude(
                request.getMaximumAltitude());

        heliTour.setFlightDistance(
                request.getFlightDistance());

        heliTour.setBestSeason(
                request.getBestSeason());

        heliTour.setLandingAllowed(
                request.getLandingAllowed());

        heliTour.setLandingDetails(
                request.getLandingDetails());

        heliTour.setOxygenAvailable(
                request.getOxygenAvailable());

        heliTour.setEmergencySupportAvailable(
                request.getEmergencySupportAvailable());

        heliTour.setSafetyInformation(
                request.getSafetyInformation());

        heliTour.setPrice(
                request.getPrice());

        heliTour.setCurrency(
                request.getCurrency());

        heliTour.setPricePerPerson(
                request.getPricePerPerson());

        if (request.getActive() != null) {
            heliTour.setActive(request.getActive());
        }

        HeliTour updated =
                heliTourRepository.save(heliTour);

        return mapToResponse(updated);
    }

    // ==========================
    // DELETE
    // ==========================

    @Override
    public void deleteHeliTour(Long id) {

        HeliTour heliTour =
                heliTourRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Heli tour not found with id: "
                                                + id
                                )
                        );

        heliTourRepository.delete(heliTour);
    }

    // ==========================
    // ENTITY → RESPONSE
    // ==========================

    private HeliTourResponse mapToResponse(
            HeliTour heliTour) {

        return HeliTourResponse.builder()
                .id(heliTour.getId())
                .heliTourCode(
                        heliTour.getHeliTourCode())
                .heliTourName(
                        heliTour.getHeliTourName())
                .region(
                        heliTour.getRegion())
                .destination(
                        heliTour.getDestination())
                .shortDescription(
                        heliTour.getShortDescription())
                .description(
                        heliTour.getDescription())
                .durationHours(
                        heliTour.getDurationHours())
                .flightType(
                        heliTour.getFlightType())
                .helicopterType(
                        heliTour.getHelicopterType())
                .departureLocation(
                        heliTour.getDepartureLocation())
                .landingLocation(
                        heliTour.getLandingLocation())
                .returnLocation(
                        heliTour.getReturnLocation())
                .minPassengers(
                        heliTour.getMinPassengers())
                .maxPassengers(
                        heliTour.getMaxPassengers())
                .maximumAltitude(
                        heliTour.getMaximumAltitude())
                .flightDistance(
                        heliTour.getFlightDistance())
                .bestSeason(
                        heliTour.getBestSeason())
                .landingAllowed(
                        heliTour.getLandingAllowed())
                .landingDetails(
                        heliTour.getLandingDetails())
                .oxygenAvailable(
                        heliTour.getOxygenAvailable())
                .emergencySupportAvailable(
                        heliTour.getEmergencySupportAvailable())
                .safetyInformation(
                        heliTour.getSafetyInformation())
                .price(
                        heliTour.getPrice())
                .currency(
                        heliTour.getCurrency())
                .pricePerPerson(
                        heliTour.getPricePerPerson())
                .active(
                        heliTour.getActive())
                .createdAt(
                        heliTour.getCreatedAt())
                .updatedAt(
                        heliTour.getUpdatedAt())
                .build();
    }
}