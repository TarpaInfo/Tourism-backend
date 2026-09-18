package com.tarpa.tourism.peakclimbing.controller;

import com.tarpa.tourism.peakclimbing.request.PeakClimbingItineraryRequest;
import com.tarpa.tourism.peakclimbing.response.PeakClimbingItineraryResponse;
import com.tarpa.tourism.peakclimbing.service.PeakClimbingItineraryService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities/peak-climbing-itineraries")
@RequiredArgsConstructor
public class PeakClimbingItineraryController {

    private final PeakClimbingItineraryService itineraryService;

    // ==========================
    // CREATE
    // ==========================

    @PostMapping
    public ResponseEntity<PeakClimbingItineraryResponse> createItinerary(
            @Valid @RequestBody PeakClimbingItineraryRequest request) {

        PeakClimbingItineraryResponse response =
                itineraryService.createItinerary(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ==========================
    // GET ALL
    // ==========================

    @GetMapping
    public ResponseEntity<List<PeakClimbingItineraryResponse>>
    getAllItineraries() {

        return ResponseEntity.ok(
                itineraryService.getAllItineraries()
        );
    }

    // ==========================
    // GET BY PEAK CLIMBING ID
    // ==========================

    @GetMapping("/peak-climbing/{peakClimbingId}")
    public ResponseEntity<List<PeakClimbingItineraryResponse>>
    getItinerariesByPeakClimbingId(
            @PathVariable Long peakClimbingId) {

        return ResponseEntity.ok(
                itineraryService
                        .getItinerariesByPeakClimbingId(peakClimbingId)
        );
    }

    // ==========================
    // GET BY ID
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<PeakClimbingItineraryResponse>
    getItineraryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                itineraryService.getItineraryById(id)
        );
    }

    // ==========================
    // UPDATE
    // ==========================

    @PutMapping("/{id}")
    public ResponseEntity<PeakClimbingItineraryResponse>
    updateItinerary(
            @PathVariable Long id,
            @Valid @RequestBody PeakClimbingItineraryRequest request) {

        return ResponseEntity.ok(
                itineraryService.updateItinerary(id, request)
        );
    }

    // ==========================
    // DELETE
    // ==========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItinerary(
            @PathVariable Long id) {

        itineraryService.deleteItinerary(id);

        return ResponseEntity.noContent().build();
    }
}