package com.tarpa.tourism.mountainexpedition.controller;

import com.tarpa.tourism.mountainexpedition.request.MountainExpeditionRequest;
import com.tarpa.tourism.mountainexpedition.response.MountainExpeditionResponse;
import com.tarpa.tourism.mountainexpedition.service.MountainExpeditionService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities/mountain-expedition")
@RequiredArgsConstructor
public class MountainExpeditionController {

    private final MountainExpeditionService mountainExpeditionService;

    // ==========================
    // CREATE
    // ==========================

    @PostMapping
    public ResponseEntity<MountainExpeditionResponse>
    createMountainExpedition(
            @Valid @RequestBody MountainExpeditionRequest request) {

        MountainExpeditionResponse response =
                mountainExpeditionService
                        .createMountainExpedition(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ==========================
    // GET ALL
    // ==========================

    @GetMapping
    public ResponseEntity<List<MountainExpeditionResponse>>
    getAllMountainExpeditions() {

        return ResponseEntity.ok(
                mountainExpeditionService
                        .getAllMountainExpeditions()
        );
    }

    // ==========================
    // GET BY ID
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<MountainExpeditionResponse>
    getMountainExpeditionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                mountainExpeditionService
                        .getMountainExpeditionById(id)
        );
    }

    // ==========================
    // UPDATE
    // ==========================

    @PutMapping("/{id}")
    public ResponseEntity<MountainExpeditionResponse>
    updateMountainExpedition(
            @PathVariable Long id,
            @Valid @RequestBody MountainExpeditionRequest request) {

        return ResponseEntity.ok(
                mountainExpeditionService
                        .updateMountainExpedition(id, request)
        );
    }

    // ==========================
    // DELETE
    // ==========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMountainExpedition(
            @PathVariable Long id) {

        mountainExpeditionService
                .deleteMountainExpedition(id);

        return ResponseEntity.noContent().build();
    }
}