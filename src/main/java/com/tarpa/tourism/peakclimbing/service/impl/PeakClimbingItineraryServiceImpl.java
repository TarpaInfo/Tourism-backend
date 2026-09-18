package com.tarpa.tourism.peakclimbing.service.impl;

import com.tarpa.tourism.exception.DuplicateResourceException;
import com.tarpa.tourism.exception.ResourceNotFoundException;
import com.tarpa.tourism.peakclimbing.entity.PeakClimbingItinerary;
import com.tarpa.tourism.peakclimbing.repository.PeakClimbingItineraryRepository;
import com.tarpa.tourism.peakclimbing.request.PeakClimbingItineraryRequest;
import com.tarpa.tourism.peakclimbing.response.PeakClimbingItineraryResponse;
import com.tarpa.tourism.peakclimbing.service.PeakClimbingItineraryService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PeakClimbingItineraryServiceImpl
        implements PeakClimbingItineraryService {

    private final PeakClimbingItineraryRepository itineraryRepository;

    // ==========================
    // CREATE
    // ==========================

    @Override
    public PeakClimbingItineraryResponse createItinerary(
            PeakClimbingItineraryRequest request) {

        if (itineraryRepository.existsByPeakClimbingIdAndDayNumber(
                request.getPeakClimbingId(),
                request.getDayNumber())) {

            throw new DuplicateResourceException(
                    "Itinerary day already exists for peak climbing ID: "
                            + request.getPeakClimbingId()
                            + ", day: "
                            + request.getDayNumber()
            );
        }

        PeakClimbingItinerary itinerary =
                PeakClimbingItinerary.builder()
                        .peakClimbingId(request.getPeakClimbingId())
                        .dayNumber(request.getDayNumber())
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .startLocation(request.getStartLocation())
                        .endLocation(request.getEndLocation())
                        .altitude(request.getAltitude())
                        .distance(request.getDistance())
                        .walkingHours(request.getWalkingHours())
                        .climbingHours(request.getClimbingHours())
                        .climbingActivity(request.getClimbingActivity())
                        .camp(request.getCamp())
                        .accommodation(request.getAccommodation())
                        .meals(request.getMeals())
                        .difficulty(request.getDifficulty())
                        .technicalRequirements(
                                request.getTechnicalRequirements())
                        .highlights(request.getHighlights())
                        .active(request.getActive() != null
                                ? request.getActive()
                                : true)
                        .build();

        PeakClimbingItinerary saved =
                itineraryRepository.save(itinerary);

        return mapToResponse(saved);
    }

    // ==========================
    // GET ALL
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<PeakClimbingItineraryResponse> getAllItineraries() {

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
    public PeakClimbingItineraryResponse getItineraryById(
            Long id) {

        PeakClimbingItinerary itinerary =
                itineraryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Peak climbing itinerary not found with id: "
                                                + id
                                )
                        );

        return mapToResponse(itinerary);
    }

    // ==========================
    // GET BY PEAK CLIMBING ID
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<PeakClimbingItineraryResponse>
    getItinerariesByPeakClimbingId(Long peakClimbingId) {

        return itineraryRepository
                .findByPeakClimbingIdOrderByDayNumberAsc(
                        peakClimbingId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================
    // UPDATE
    // ==========================

    @Override
    public PeakClimbingItineraryResponse updateItinerary(
            Long id,
            PeakClimbingItineraryRequest request) {

        PeakClimbingItinerary itinerary =
                itineraryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Peak climbing itinerary not found with id: "
                                                + id
                                )
                        );

        boolean peakClimbingChanged =
                !itinerary.getPeakClimbingId()
                        .equals(request.getPeakClimbingId());

        boolean dayChanged =
                !itinerary.getDayNumber()
                        .equals(request.getDayNumber());

        if ((peakClimbingChanged || dayChanged)
                && itineraryRepository
                .existsByPeakClimbingIdAndDayNumber(
                        request.getPeakClimbingId(),
                        request.getDayNumber())) {

            throw new DuplicateResourceException(
                    "Itinerary day already exists for peak climbing ID: "
                            + request.getPeakClimbingId()
                            + ", day: "
                            + request.getDayNumber()
            );
        }

        itinerary.setPeakClimbingId(
                request.getPeakClimbingId());

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

        itinerary.setClimbingHours(
                request.getClimbingHours());

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

        if (request.getActive() != null) {
            itinerary.setActive(request.getActive());
        }

        PeakClimbingItinerary updated =
                itineraryRepository.save(itinerary);

        return mapToResponse(updated);
    }

    // ==========================
    // DELETE
    // ==========================

    @Override
    public void deleteItinerary(Long id) {

        PeakClimbingItinerary itinerary =
                itineraryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Peak climbing itinerary not found with id: "
                                                + id
                                )
                        );

        itineraryRepository.delete(itinerary);
    }

    // ==========================
    // ENTITY → RESPONSE
    // ==========================

    private PeakClimbingItineraryResponse mapToResponse(
            PeakClimbingItinerary itinerary) {

        return PeakClimbingItineraryResponse.builder()
                .id(itinerary.getId())
                .peakClimbingId(itinerary.getPeakClimbingId())
                .dayNumber(itinerary.getDayNumber())
                .title(itinerary.getTitle())
                .description(itinerary.getDescription())
                .startLocation(itinerary.getStartLocation())
                .endLocation(itinerary.getEndLocation())
                .altitude(itinerary.getAltitude())
                .distance(itinerary.getDistance())
                .walkingHours(itinerary.getWalkingHours())
                .climbingHours(itinerary.getClimbingHours())
                .climbingActivity(
                        itinerary.getClimbingActivity())
                .camp(itinerary.getCamp())
                .accommodation(itinerary.getAccommodation())
                .meals(itinerary.getMeals())
                .difficulty(itinerary.getDifficulty())
                .technicalRequirements(
                        itinerary.getTechnicalRequirements())
                .highlights(itinerary.getHighlights())
                .active(itinerary.getActive())
                .build();
    }
}