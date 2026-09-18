package com.tarpa.tourism.helitour.controller;

import com.tarpa.tourism.helitour.request.HeliTourRequest;
import com.tarpa.tourism.helitour.response.HeliTourResponse;
import com.tarpa.tourism.helitour.service.HeliTourService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities/heli-tours")
@RequiredArgsConstructor
public class HeliTourController {

    private final HeliTourService heliTourService;

    // ==========================
    // CREATE
    // ==========================

    @PostMapping
    public ResponseEntity<HeliTourResponse> createHeliTour(
            @Valid @RequestBody HeliTourRequest request) {

        HeliTourResponse response =
                heliTourService.createHeliTour(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ==========================
    // GET ALL
    // ==========================

    @GetMapping
    public ResponseEntity<List<HeliTourResponse>> getAllHeliTours() {

        return ResponseEntity.ok(
                heliTourService.getAllHeliTours()
        );
    }

    // ==========================
    // GET BY ID
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<HeliTourResponse> getHeliTourById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                heliTourService.getHeliTourById(id)
        );
    }

    // ==========================
    // UPDATE
    // ==========================

    @PutMapping("/{id}")
    public ResponseEntity<HeliTourResponse> updateHeliTour(
            @PathVariable Long id,
            @Valid @RequestBody HeliTourRequest request) {

        return ResponseEntity.ok(
                heliTourService.updateHeliTour(id, request)
        );
    }

    // ==========================
    // DELETE
    // ==========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHeliTour(
            @PathVariable Long id) {

        heliTourService.deleteHeliTour(id);

        return ResponseEntity.noContent().build();
    }
}