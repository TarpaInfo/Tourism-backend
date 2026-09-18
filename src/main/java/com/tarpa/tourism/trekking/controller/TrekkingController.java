package com.tarpa.tourism.trekking.controller;

import com.tarpa.tourism.trekking.request.TrekkingRequest;
import com.tarpa.tourism.trekking.response.TrekkingResponse;
import com.tarpa.tourism.trekking.service.TrekkingService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities/trekkings")
@RequiredArgsConstructor
public class TrekkingController {

    private final TrekkingService trekkingService;

    // ==========================
    // CREATE TREKKING
    // ==========================

    @PostMapping
    public ResponseEntity<TrekkingResponse> createTrekking(
            @Valid @RequestBody TrekkingRequest request) {

        TrekkingResponse response =
                trekkingService.createTrekking(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ==========================
    // GET ALL TREKKINGS
    // ==========================

    @GetMapping
    public ResponseEntity<List<TrekkingResponse>> getAllTrekkings() {

        return ResponseEntity.ok(
                trekkingService.getAllTrekkings()
        );
    }

    // ==========================
    // GET TREKKING BY ID
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<TrekkingResponse> getTrekkingById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                trekkingService.getTrekkingById(id)
        );
    }

    // ==========================
    // UPDATE TREKKING
    // ==========================

    @PutMapping("/{id}")
    public ResponseEntity<TrekkingResponse> updateTrekking(
            @PathVariable Long id,
            @Valid @RequestBody TrekkingRequest request) {

        return ResponseEntity.ok(
                trekkingService.updateTrekking(id, request)
        );
    }

    // ==========================
    // DELETE TREKKING
    // ==========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrekking(
            @PathVariable Long id) {

        trekkingService.deleteTrekking(id);

        return ResponseEntity.noContent().build();
    }
}