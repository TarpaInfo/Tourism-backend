package com.tarpa.tourism.trekking.service.impl;

import com.tarpa.tourism.exception.DuplicateResourceException;
import com.tarpa.tourism.exception.ResourceNotFoundException;
import com.tarpa.tourism.trekking.entity.TrekkingItinerary;
import com.tarpa.tourism.trekking.repository.TrekkingItineraryRepository;
import com.tarpa.tourism.trekking.request.TrekkingItineraryRequest;
import com.tarpa.tourism.trekking.response.TrekkingItineraryResponse;
import com.tarpa.tourism.trekking.service.TrekkingItineraryService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TrekkingItineraryServiceImpl
        implements TrekkingItineraryService {

    private final TrekkingItineraryRepository itineraryRepository;

    // ==========================
    // CREATE
    // ==========================

    @Override
    public TrekkingItineraryResponse createItinerary(
            TrekkingItineraryRequest request) {

        if (itineraryRepository.existsByTrekkingIdAndDayNumber(
                request.getTrekkingId(),
                request.getDayNumber())) {

            throw new DuplicateResourceException(
                    "Itinerary day already exists for trekking ID: "
                            + request.getTrekkingId()
                            + ", day: "
                            + request.getDayNumber()
            );
        }

        TrekkingItinerary itinerary =
                TrekkingItinerary.builder()
                        .trekkingId(request.getTrekkingId())
                        .dayNumber(request.getDayNumber())
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .startLocation(request.getStartLocation())
                        .endLocation(request.getEndLocation())
                        .altitude(request.getAltitude())
                        .distance(request.getDistance())
                        .walkingHours(request.getWalkingHours())
                        .accommodation(request.getAccommodation())
                        .meals(request.getMeals())
                        .difficulty(request.getDifficulty())
                        .highlights(request.getHighlights())
                        .active(request.getActive() != null
                                ? request.getActive()
                                : true)
                        .build();

        TrekkingItinerary saved =
                itineraryRepository.save(itinerary);

        return mapToResponse(saved);
    }

    // ==========================
    // GET ALL
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<TrekkingItineraryResponse> getAllItineraries() {

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
    public TrekkingItineraryResponse getItineraryById(
            Long id) {

        TrekkingItinerary itinerary =
                itineraryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Trekking itinerary not found with id: "
                                                + id
                                )
                        );

        return mapToResponse(itinerary);
    }

    // ==========================
    // GET BY TREKKING ID
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<TrekkingItineraryResponse>
    getItinerariesByTrekkingId(Long trekkingId) {

        return itineraryRepository
                .findByTrekkingIdOrderByDayNumberAsc(trekkingId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================
    // UPDATE
    // ==========================

    @Override
    public TrekkingItineraryResponse updateItinerary(
            Long id,
            TrekkingItineraryRequest request) {

        TrekkingItinerary itinerary =
                itineraryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Trekking itinerary not found with id: "
                                                + id
                                )
                        );

        boolean trekkingChanged =
                !itinerary.getTrekkingId()
                        .equals(request.getTrekkingId());

        boolean dayChanged =
                !itinerary.getDayNumber()
                        .equals(request.getDayNumber());

        if ((trekkingChanged || dayChanged)
                && itineraryRepository
                .existsByTrekkingIdAndDayNumber(
                        request.getTrekkingId(),
                        request.getDayNumber())) {

            throw new DuplicateResourceException(
                    "Itinerary day already exists for trekking ID: "
                            + request.getTrekkingId()
                            + ", day: "
                            + request.getDayNumber()
            );
        }

        itinerary.setTrekkingId(
                request.getTrekkingId());

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

        itinerary.setAccommodation(
                request.getAccommodation());

        itinerary.setMeals(
                request.getMeals());

        itinerary.setDifficulty(
                request.getDifficulty());

        itinerary.setHighlights(
                request.getHighlights());

        if (request.getActive() != null) {
            itinerary.setActive(request.getActive());
        }

        TrekkingItinerary updated =
                itineraryRepository.save(itinerary);

        return mapToResponse(updated);
    }

    // ==========================
    // DELETE
    // ==========================

    @Override
    public void deleteItinerary(Long id) {

        TrekkingItinerary itinerary =
                itineraryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Trekking itinerary not found with id: "
                                                + id
                                )
                        );

        itineraryRepository.delete(itinerary);
    }

    // ==========================
    // ENTITY → RESPONSE
    // ==========================

    private TrekkingItineraryResponse mapToResponse(
            TrekkingItinerary itinerary) {

        return TrekkingItineraryResponse.builder()
                .id(itinerary.getId())
                .trekkingId(itinerary.getTrekkingId())
                .dayNumber(itinerary.getDayNumber())
                .title(itinerary.getTitle())
                .description(itinerary.getDescription())
                .startLocation(itinerary.getStartLocation())
                .endLocation(itinerary.getEndLocation())
                .altitude(itinerary.getAltitude())
                .distance(itinerary.getDistance())
                .walkingHours(itinerary.getWalkingHours())
                .accommodation(itinerary.getAccommodation())
                .meals(itinerary.getMeals())
                .difficulty(itinerary.getDifficulty())
                .highlights(itinerary.getHighlights())
                .active(itinerary.getActive())
                .build();
    }
}