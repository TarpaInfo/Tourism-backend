package com.tarpa.tourism.helitour.service;

import com.tarpa.tourism.helitour.request.HeliTourRequest;
import com.tarpa.tourism.helitour.response.HeliTourResponse;

import java.util.List;

public interface HeliTourService {

    HeliTourResponse createHeliTour(
            HeliTourRequest request
    );

    List<HeliTourResponse> getAllHeliTours();

    HeliTourResponse getHeliTourById(Long id);

    HeliTourResponse updateHeliTour(
            Long id,
            HeliTourRequest request
    );

    void deleteHeliTour(Long id);
}