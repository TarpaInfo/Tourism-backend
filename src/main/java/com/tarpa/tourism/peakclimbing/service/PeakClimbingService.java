package com.tarpa.tourism.peakclimbing.service;

import com.tarpa.tourism.peakclimbing.request.PeakClimbingRequest;
import com.tarpa.tourism.peakclimbing.response.PeakClimbingResponse;

import java.util.List;

public interface PeakClimbingService {

    PeakClimbingResponse createPeakClimbing(
            PeakClimbingRequest request
    );

    List<PeakClimbingResponse> getAllPeakClimbings();

    PeakClimbingResponse getPeakClimbingById(Long id);

    PeakClimbingResponse updatePeakClimbing(
            Long id,
            PeakClimbingRequest request
    );

    void deletePeakClimbing(Long id);
}