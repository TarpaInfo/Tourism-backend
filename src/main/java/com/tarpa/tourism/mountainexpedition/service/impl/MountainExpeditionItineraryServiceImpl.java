package com.tarpa.tourism.mountainexpedition.service.impl;

import com.tarpa.tourism.exception.DuplicateResourceException;
import com.tarpa.tourism.exception.ResourceNotFoundException;
import com.tarpa.tourism.mountainexpedition.entity.MountainExpeditionItinerary;
import com.tarpa.tourism.mountainexpedition.repository.MountainExpeditionItineraryRepository;
import com.tarpa.tourism.mountainexpedition.request.MountainExpeditionItineraryRequest;
import com.tarpa.tourism.mountainexpedition.response.MountainExpeditionItineraryResponse;
import com.tarpa.tourism.mountainexpedition.service.MountainExpeditionItineraryService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MountainExpeditionItineraryServiceImpl
        implements MountainExpeditionItineraryService {

    private final MountainExpeditionItineraryRepository itineraryRepository;

    // ==========================
    // CREATE
    // ==========================

    @Override
    public MountainExpeditionItineraryResponse createItinerary(
            MountainExpeditionItineraryRequest request) {

        if (itineraryRepository.existsByMountainExpeditionIdAndDayNumber(
                request.getMountainExpeditionId(),
                request.getDayNumber())) {

            throw new DuplicateResourceException(
                    "Itinerary day already exists for mountain expedition ID: "
                            + request.getMountainExpeditionId()
                            + ", day: "
                            + request.getDayNumber()
            );
        }

        MountainExpeditionItinerary itinerary =
                MountainExpeditionItinerary.builder()
                        .mountainExpeditionId(
                                request.getMountainExpeditionId())
                        .dayNumber(request.getDayNumber())
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .startLocation(request.getStartLocation())
                        .endLocation(request.getEndLocation())
                        .altitude(request.getAltitude())
                        .distance(request.getDistance())
                        .walkingHours(request.getWalkingHours())
                        .expeditionHours(request.getExpeditionHours())
                        .expeditionActivity(
                                request.getExpeditionActivity())
                        .acclimatization(
                                request.getAcclimatization())
                        .climbingActivity(
                                request.getClimbingActivity())
                        .camp(request.getCamp())
                        .accommodation(request.getAccommodation())
                        .meals(request.getMeals())
                        .difficulty(request.getDifficulty())
                        .technicalRequirements(
                                request.getTechnicalRequirements())
                        .highlights(request.getHighlights())
                        .expeditionNotes(
                                request.getExpeditionNotes())
                        .active(request.getActive() != null
                                ? request.getActive()
                                : true)
                        .build();

        MountainExpeditionItinerary saved =
                itineraryRepository.save(itinerary);

        return mapToResponse(saved);
    }

    // ==========================
    // GET ALL
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<MountainExpeditionItineraryResponse>
    getAllItineraries() {

        return itineraryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================
    // GET BY ID
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public MountainExpeditionItineraryResponse getItineraryById(
            Long id) {

        MountainExpeditionItinerary itinerary =
                itineraryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Mountain expedition itinerary not found with id: "
                                                + id
                                )
                        );

        return mapToResponse(itinerary);
    }

    // ==========================
    // GET BY EXPEDITION ID
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<MountainExpeditionItineraryResponse>
    getItinerariesByMountainExpeditionId(
            Long mountainExpeditionId) {

        return itineraryRepository
                .findByMountainExpeditionIdOrderByDayNumberAsc(
                        mountainExpeditionId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================
    // UPDATE
    // ==========================

    @Override
    public MountainExpeditionItineraryResponse updateItinerary(
            Long id,
            MountainExpeditionItineraryRequest request) {

        MountainExpeditionItinerary itinerary =
                itineraryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Mountain expedition itinerary not found with id: "
                                                + id
                                )
                        );

        boolean expeditionChanged =
                !itinerary.getMountainExpeditionId()
                        .equals(request.getMountainExpeditionId());

        boolean dayChanged =
                !itinerary.getDayNumber()
                        .equals(request.getDayNumber());

        if ((expeditionChanged || dayChanged)
                && itineraryRepository
                .existsByMountainExpeditionIdAndDayNumber(
                        request.getMountainExpeditionId(),
                        request.getDayNumber())) {

            throw new DuplicateResourceException(
                    "Itinerary day already exists for mountain expedition ID: "
                            + request.getMountainExpeditionId()
                            + ", day: "
                            + request.getDayNumber()
            );
        }

        itinerary.setMountainExpeditionId(
                request.getMountainExpeditionId());

        itinerary.setDayNumber(
                request.getDayNumber());

        itinerary.setTitle(
                request.getTitle());

        itinerary.setDescription(
                request.getDescription());

        itinerary.setStartLocation(
                request.getStartLocation());

        itinerary.setEndLocation(
                request.getEndLocation());

        itinerary.setAltitude(
                request.getAltitude());

        itinerary.setDistance(
                request.getDistance());

        itinerary.setWalkingHours(
                request.getWalkingHours());

        itinerary.setExpeditionHours(
                request.getExpeditionHours());

        itinerary.setExpeditionActivity(
                request.getExpeditionActivity());

        itinerary.setAcclimatization(
                request.getAcclimatization());

        itinerary.setClimbingActivity(
                request.getClimbingActivity());

        itinerary.setCamp(
                request.getCamp());

        itinerary.setAccommodation(
                request.getAccommodation());

        itinerary.setMeals(
                request.getMeals());

        itinerary.setDifficulty(
                request.getDifficulty());

        itinerary.setTechnicalRequirements(
                request.getTechnicalRequirements());

        itinerary.setHighlights(
                request.getHighlights());

        itinerary.setExpeditionNotes(
                request.getExpeditionNotes());

        if (request.getActive() != null) {
            itinerary.setActive(request.getActive());
        }

        MountainExpeditionItinerary updated =
                itineraryRepository.save(itinerary);

        return mapToResponse(updated);
    }

    // ==========================
    // DELETE
    // ==========================

    @Override
    public void deleteItinerary(Long id) {

        MountainExpeditionItinerary itinerary =
                itineraryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Mountain expedition itinerary not found with id: "
                                                + id
                                )
                        );

        itineraryRepository.delete(itinerary);
    }

    // ==========================
    // ENTITY → RESPONSE
    // ==========================

    private MountainExpeditionItineraryResponse mapToResponse(
            MountainExpeditionItinerary itinerary) {

        return MountainExpeditionItineraryResponse.builder()
                .id(itinerary.getId())
                .mountainExpeditionId(
                        itinerary.getMountainExpeditionId())
                .dayNumber(itinerary.getDayNumber())
                .title(itinerary.getTitle())
                .description(itinerary.getDescription())
                .startLocation(itinerary.getStartLocation())
                .endLocation(itinerary.getEndLocation())
                .altitude(itinerary.getAltitude())
                .distance(itinerary.getDistance())
                .walkingHours(itinerary.getWalkingHours())
                .expeditionHours(itinerary.getExpeditionHours())
                .expeditionActivity(
                        itinerary.getExpeditionActivity())
                .acclimatization(
                        itinerary.getAcclimatization())
                .climbingActivity(
                        itinerary.getClimbingActivity())
                .camp(itinerary.getCamp())
                .accommodation(itinerary.getAccommodation())
                .meals(itinerary.getMeals())
                .difficulty(itinerary.getDifficulty())
                .technicalRequirements(
                        itinerary.getTechnicalRequirements())
                .highlights(itinerary.getHighlights())
                .expeditionNotes(
                        itinerary.getExpeditionNotes())
                .active(itinerary.getActive())
                .build();
    }
}