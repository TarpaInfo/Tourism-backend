package com.tarpa.tourism.peakclimbing.controller;

import com.tarpa.tourism.peakclimbing.request.PeakClimbingRequest;
import com.tarpa.tourism.peakclimbing.response.PeakClimbingResponse;
import com.tarpa.tourism.peakclimbing.service.PeakClimbingService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities/peak-climbings")
@RequiredArgsConstructor
public class PeakClimbingController {

    private final PeakClimbingService peakClimbingService;

    // ==========================
    // CREATE PEAK CLIMBING
    // ==========================

    @PostMapping
    public ResponseEntity<PeakClimbingResponse> createPeakClimbing(
            @Valid @RequestBody PeakClimbingRequest request) {

        PeakClimbingResponse response =
                peakClimbingService.createPeakClimbing(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ==========================
    // GET ALL PEAK CLIMBINGS
    // ==========================

    @GetMapping
    public ResponseEntity<List<PeakClimbingResponse>>
    getAllPeakClimbings() {

        return ResponseEntity.ok(
                peakClimbingService.getAllPeakClimbings()
        );
    }

    // ==========================
    // GET PEAK CLIMBING BY ID
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<PeakClimbingResponse> getPeakClimbingById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                peakClimbingService.getPeakClimbingById(id)
        );
    }

    // ==========================
    // UPDATE PEAK CLIMBING
    // ==========================

    @PutMapping("/{id}")
    public ResponseEntity<PeakClimbingResponse> updatePeakClimbing(
            @PathVariable Long id,
            @Valid @RequestBody PeakClimbingRequest request) {

        return ResponseEntity.ok(
                peakClimbingService.updatePeakClimbing(id, request)
        );
    }

    // ==========================
    // DELETE PEAK CLIMBING
    // ==========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeakClimbing(
            @PathVariable Long id) {

        peakClimbingService.deletePeakClimbing(id);

        return ResponseEntity.noContent().build();
    }
}