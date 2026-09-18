package com.tarpa.tourism.trekking.controller;

import com.tarpa.tourism.trekking.request.TrekkingItineraryRequest;
import com.tarpa.tourism.trekking.response.TrekkingItineraryResponse;
import com.tarpa.tourism.trekking.service.TrekkingItineraryService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities/trekking-itineraries")
@RequiredArgsConstructor
public class TrekkingItineraryController {

    private final TrekkingItineraryService itineraryService;

    // ==========================
    // CREATE
    // ==========================

    @PostMapping
    public ResponseEntity<TrekkingItineraryResponse> createItinerary(
            @Valid @RequestBody TrekkingItineraryRequest request) {

        TrekkingItineraryResponse response =
                itineraryService.createItinerary(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ==========================
    // GET ALL
    // ==========================

    @GetMapping
    public ResponseEntity<List<TrekkingItineraryResponse>>
    getAllItineraries() {

        return ResponseEntity.ok(
                itineraryService.getAllItineraries()
        );
    }

    // ==========================
    // GET BY TREKKING ID
    // ==========================

    @GetMapping("/trekking/{trekkingId}")
    public ResponseEntity<List<TrekkingItineraryResponse>>
    getItinerariesByTrekkingId(
            @PathVariable Long trekkingId) {

        return ResponseEntity.ok(
                itineraryService
                        .getItinerariesByTrekkingId(trekkingId)
        );
    }

    // ==========================
    // GET BY ID
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<TrekkingItineraryResponse>
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
    public ResponseEntity<TrekkingItineraryResponse>
    updateItinerary(
            @PathVariable Long id,
            @Valid @RequestBody TrekkingItineraryRequest request) {

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