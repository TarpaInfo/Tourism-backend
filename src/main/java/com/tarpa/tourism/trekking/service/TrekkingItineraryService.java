package com.tarpa.tourism.trekking.service;

import com.tarpa.tourism.trekking.request.TrekkingItineraryRequest;
import com.tarpa.tourism.trekking.response.TrekkingItineraryResponse;

import java.util.List;

public interface TrekkingItineraryService {

    TrekkingItineraryResponse createItinerary(
            TrekkingItineraryRequest request
    );

    List<TrekkingItineraryResponse> getAllItineraries();

    TrekkingItineraryResponse getItineraryById(Long id);

    List<TrekkingItineraryResponse> getItinerariesByTrekkingId(
            Long trekkingId
    );

    TrekkingItineraryResponse updateItinerary(
            Long id,
            TrekkingItineraryRequest request
    );

    void deleteItinerary(Long id);
}