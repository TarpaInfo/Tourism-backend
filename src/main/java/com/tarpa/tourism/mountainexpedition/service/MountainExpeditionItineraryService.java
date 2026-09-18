package com.tarpa.tourism.mountainexpedition.service;

import com.tarpa.tourism.mountainexpedition.request.MountainExpeditionItineraryRequest;
import com.tarpa.tourism.mountainexpedition.response.MountainExpeditionItineraryResponse;

import java.util.List;

public interface MountainExpeditionItineraryService {

    MountainExpeditionItineraryResponse createItinerary(
            MountainExpeditionItineraryRequest request
    );

    List<MountainExpeditionItineraryResponse> getAllItineraries();

    MountainExpeditionItineraryResponse getItineraryById(Long id);

    List<MountainExpeditionItineraryResponse>
    getItinerariesByMountainExpeditionId(Long mountainExpeditionId);

    MountainExpeditionItineraryResponse updateItinerary(
            Long id,
            MountainExpeditionItineraryRequest request
    );

    void deleteItinerary(Long id);
}