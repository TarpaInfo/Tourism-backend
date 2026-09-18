package com.tarpa.tourism.mountainexpedition.controller;

import com.tarpa.tourism.mountainexpedition.request.MountainExpeditionItineraryRequest;
import com.tarpa.tourism.mountainexpedition.response.MountainExpeditionItineraryResponse;
import com.tarpa.tourism.mountainexpedition.service.MountainExpeditionItineraryService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities/mountain-expedition-itineraries")
@RequiredArgsConstructor
public class MountainExpeditionItineraryController {

    private final MountainExpeditionItineraryService itineraryService;

    // ==========================
    // CREATE
    // ==========================

    @PostMapping
    public ResponseEntity<MountainExpeditionItineraryResponse> createItinerary(
            @Valid @RequestBody MountainExpeditionItineraryRequest request) {

        MountainExpeditionItineraryResponse response =
                itineraryService.createItinerary(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ==========================
    // GET ALL
    // ==========================

    @GetMapping
    public ResponseEntity<List<MountainExpeditionItineraryResponse>>
    getAllItineraries() {

        return ResponseEntity.ok(
                itineraryService.getAllItineraries()
        );
    }

    // ==========================
    // GET BY MOUNTAIN EXPEDITION ID
    // ==========================

    @GetMapping("/mountain-expedition/{mountainExpeditionId}")
    public ResponseEntity<List<MountainExpeditionItineraryResponse>>
    getItinerariesByMountainExpeditionId(
            @PathVariable Long mountainExpeditionId) {

        return ResponseEntity.ok(
                itineraryService
                        .getItinerariesByMountainExpeditionId(
                                mountainExpeditionId
                        )
        );
    }

    // ==========================
    // GET BY ITINERARY ID
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<MountainExpeditionItineraryResponse>
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
    public ResponseEntity<MountainExpeditionItineraryResponse>
    updateItinerary(
            @PathVariable Long id,
            @Valid @RequestBody MountainExpeditionItineraryRequest request) {

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