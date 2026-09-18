package com.tarpa.tourism.tour.service;

import com.tarpa.tourism.tour.request.TourPackageRequest;
import com.tarpa.tourism.tour.response.TourPackageResponse;

import java.util.List;

public interface TourPackageService {

    TourPackageResponse createPackage(TourPackageRequest request);

    TourPackageResponse getPackageById(Long id);

    List<TourPackageResponse> getAllPackages();

    List<TourPackageResponse> getActivePackages();

    List<TourPackageResponse> getPackagesByType(String packageType);

    List<TourPackageResponse> getPackagesByDestination(String destination);

    TourPackageResponse updatePackage(
            Long id,
            TourPackageRequest request
    );

    void deletePackage(Long id);
}