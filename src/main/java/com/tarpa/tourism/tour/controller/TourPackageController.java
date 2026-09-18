package com.tarpa.tourism.tour.controller;

import com.tarpa.tourism.tour.request.TourPackageRequest;
import com.tarpa.tourism.tour.response.TourPackageResponse;
import com.tarpa.tourism.tour.service.TourPackageService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities/tour-packages")
@RequiredArgsConstructor
public class TourPackageController {

    private final TourPackageService tourPackageService;

    // =========================================================
    // CREATE TOUR PACKAGE
    // =========================================================

    @PostMapping
    public ResponseEntity<TourPackageResponse> createPackage(
            @Valid @RequestBody TourPackageRequest request) {

        TourPackageResponse response =
                tourPackageService.createPackage(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // =========================================================
    // GET ALL PACKAGES
    // =========================================================

    @GetMapping
    public ResponseEntity<List<TourPackageResponse>> getAllPackages() {

        return ResponseEntity.ok(
                tourPackageService.getAllPackages()
        );
    }

    // =========================================================
    // GET PACKAGE BY ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<TourPackageResponse> getPackageById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                tourPackageService.getPackageById(id)
        );
    }

    // =========================================================
    // GET ACTIVE PACKAGES
    // =========================================================

    @GetMapping("/active")
    public ResponseEntity<List<TourPackageResponse>> getActivePackages() {

        return ResponseEntity.ok(
                tourPackageService.getActivePackages()
        );
    }

    // =========================================================
    // GET PACKAGES BY TYPE
    // =========================================================

    @GetMapping("/type/{packageType}")
    public ResponseEntity<List<TourPackageResponse>> getPackagesByType(
            @PathVariable String packageType) {

        return ResponseEntity.ok(
                tourPackageService.getPackagesByType(packageType)
        );
    }

    // =========================================================
    // GET PACKAGES BY DESTINATION
    // =========================================================

    @GetMapping("/destination/{destination}")
    public ResponseEntity<List<TourPackageResponse>> getPackagesByDestination(
            @PathVariable String destination) {

        return ResponseEntity.ok(
                tourPackageService.getPackagesByDestination(destination)
        );
    }

    // =========================================================
    // UPDATE PACKAGE
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<TourPackageResponse> updatePackage(
            @PathVariable Long id,
            @Valid @RequestBody TourPackageRequest request) {

        return ResponseEntity.ok(
                tourPackageService.updatePackage(id, request)
        );
    }

    // =========================================================
    // DELETE PACKAGE
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(
            @PathVariable Long id) {

        tourPackageService.deletePackage(id);

        return ResponseEntity.noContent().build();
    }
}