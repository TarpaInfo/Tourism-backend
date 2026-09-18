package com.tarpa.tourism.peakclimbing.service;

import com.tarpa.tourism.peakclimbing.request.PeakClimbingItineraryRequest;
import com.tarpa.tourism.peakclimbing.response.PeakClimbingItineraryResponse;

import java.util.List;

public interface PeakClimbingItineraryService {

    PeakClimbingItineraryResponse createItinerary(
            PeakClimbingItineraryRequest request
    );

    List<PeakClimbingItineraryResponse> getAllItineraries();

    PeakClimbingItineraryResponse getItineraryById(Long id);

    List<PeakClimbingItineraryResponse> getItinerariesByPeakClimbingId(
            Long peakClimbingId
    );

    PeakClimbingItineraryResponse updateItinerary(
            Long id,
            PeakClimbingItineraryRequest request
    );

    void deleteItinerary(Long id);
}