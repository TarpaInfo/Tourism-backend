package com.tarpa.tourism.tour.service;

import com.tarpa.tourism.tour.request.TourItineraryRequest;
import com.tarpa.tourism.tour.response.TourItineraryResponse;

import java.util.List;

public interface TourItineraryService {

    TourItineraryResponse createItinerary(
            TourItineraryRequest request
    );

    List<TourItineraryResponse> getAllItineraries();

    TourItineraryResponse getItineraryById(Long id);

    List<TourItineraryResponse> getItinerariesByTourPackageId(
            Long tourPackageId
    );

    void deleteItinerary(Long id);
}