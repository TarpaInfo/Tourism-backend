package com.tarpa.tourism.tour.controller;

import com.tarpa.tourism.tour.request.TourItineraryRequest;
import com.tarpa.tourism.tour.response.TourItineraryResponse;
import com.tarpa.tourism.tour.service.TourItineraryService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities/tour-itineraries")
@RequiredArgsConstructor
public class TourItineraryController {

    private final TourItineraryService tourItineraryService;


    // ==========================
    // CREATE ITINERARY
    // ==========================

    @PostMapping
    public ResponseEntity<TourItineraryResponse> createItinerary(
            @Valid @RequestBody TourItineraryRequest request) {

        TourItineraryResponse response =
                tourItineraryService.createItinerary(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // ==========================
    // GET ALL ITINERARIES
    // ==========================

    @GetMapping
    public ResponseEntity<List<TourItineraryResponse>>
    getAllItineraries() {

        return ResponseEntity.ok(
                tourItineraryService.getAllItineraries()
        );
    }


    // ==========================
    // GET ITINERARY BY ID
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<TourItineraryResponse>
    getItineraryById(@PathVariable Long id) {

        return ResponseEntity.ok(
                tourItineraryService.getItineraryById(id)
        );
    }


    // ==========================
    // GET ITINERARIES BY TOUR PACKAGE
    // ==========================

    @GetMapping("/tour-package/{tourPackageId}")
    public ResponseEntity<List<TourItineraryResponse>>
    getItinerariesByTourPackageId(
            @PathVariable Long tourPackageId) {

        return ResponseEntity.ok(
                tourItineraryService
                        .getItinerariesByTourPackageId(tourPackageId)
        );
    }


    // ==========================
    // DELETE ITINERARY
    // ==========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItinerary(
            @PathVariable Long id) {

        tourItineraryService.deleteItinerary(id);

        return ResponseEntity.noContent().build();
    }
}