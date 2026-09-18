package com.tarpa.tourism.tour.service.impl;

import com.tarpa.tourism.exception.ResourceNotFoundException;
import com.tarpa.tourism.tour.entity.TourItinerary;
import com.tarpa.tourism.tour.entity.TourPackage;
import com.tarpa.tourism.tour.repository.TourItineraryRepository;
import com.tarpa.tourism.tour.repository.TourPackageRepository;
import com.tarpa.tourism.tour.request.TourItineraryRequest;
import com.tarpa.tourism.tour.response.TourItineraryResponse;
import com.tarpa.tourism.tour.service.TourItineraryService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourItineraryServiceImpl
        implements TourItineraryService {

    private final TourItineraryRepository tourItineraryRepository;

    private final TourPackageRepository tourPackageRepository;


    // ==========================
    // CREATE ITINERARY
    // ==========================

    @Override
    public TourItineraryResponse createItinerary(
            TourItineraryRequest request) {

        TourPackage tourPackage =
                tourPackageRepository.findById(
                        request.getTourPackageId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tour package not found with id: "
                                        + request.getTourPackageId()
                        )
                );

        if (tourItineraryRepository
                .existsByTourPackageIdAndDayNumber(
                        request.getTourPackageId(),
                        request.getDayNumber()
                )) {

            throw new RuntimeException(
                    "Itinerary day already exists for this tour package: Day "
                            + request.getDayNumber()
            );
        }

        TourItinerary itinerary =
                TourItinerary.builder()
                        .tourPackage(tourPackage)
                        .dayNumber(request.getDayNumber())
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .startLocation(request.getStartLocation())
                        .endLocation(request.getEndLocation())
                        .accommodation(request.getAccommodation())
                        .meals(request.getMeals())
                        .altitude(request.getAltitude())
                        .distance(request.getDistance())
                        .active(true)
                        .build();

        TourItinerary saved =
                tourItineraryRepository.save(itinerary);

        return mapToResponse(saved);
    }


    // ==========================
    // GET ALL ITINERARIES
    // ==========================

    @Override
    public List<TourItineraryResponse> getAllItineraries() {

        return tourItineraryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // ==========================
    // GET ITINERARY BY ID
    // ==========================

    @Override
    public TourItineraryResponse getItineraryById(
            Long id) {

        TourItinerary itinerary =
                tourItineraryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Tour itinerary not found with id: "
                                                + id
                                )
                        );

        return mapToResponse(itinerary);
    }


    // ==========================
    // GET ITINERARIES BY TOUR PACKAGE
    // ==========================

    @Override
    public List<TourItineraryResponse>
    getItinerariesByTourPackageId(Long tourPackageId) {

        if (!tourPackageRepository.existsById(tourPackageId)) {

            throw new ResourceNotFoundException(
                    "Tour package not found with id: "
                            + tourPackageId
            );
        }

        return tourItineraryRepository
                .findByTourPackageId(tourPackageId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // ==========================
    // DELETE ITINERARY
    // ==========================

    @Override
    public void deleteItinerary(Long id) {

        TourItinerary itinerary =
                tourItineraryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Tour itinerary not found with id: "
                                                + id
                                )
                        );

        tourItineraryRepository.delete(itinerary);
    }


    // ==========================
    // ENTITY → RESPONSE
    // ==========================

    private TourItineraryResponse mapToResponse(
            TourItinerary itinerary) {

        return TourItineraryResponse.builder()
                .id(itinerary.getId())
                .tourPackageId(
                        itinerary.getTourPackage().getId()
                )
                .dayNumber(itinerary.getDayNumber())
                .title(itinerary.getTitle())
                .description(itinerary.getDescription())
                .startLocation(itinerary.getStartLocation())
                .endLocation(itinerary.getEndLocation())
                .accommodation(itinerary.getAccommodation())
                .meals(itinerary.getMeals())
                .altitude(itinerary.getAltitude())
                .distance(itinerary.getDistance())
                .active(itinerary.getActive())
                .build();
    }
}