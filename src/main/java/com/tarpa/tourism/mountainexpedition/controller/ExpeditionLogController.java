package com.tarpa.tourism.mountainexpedition.controller;

import com.tarpa.tourism.mountainexpedition.request.ExpeditionLogRequest;
import com.tarpa.tourism.mountainexpedition.response.ExpeditionLogResponse;
import com.tarpa.tourism.mountainexpedition.enums.ActivityCategory;
import com.tarpa.tourism.mountainexpedition.service.ExpeditionLogService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expedition-logs")
@RequiredArgsConstructor
public class ExpeditionLogController {

    private final ExpeditionLogService expeditionLogService;

    // ==========================
    // CREATE LOG / CHECK-IN
    // ==========================
    @PostMapping
    public ResponseEntity<ExpeditionLogResponse> createLog(
            @Valid @RequestBody ExpeditionLogRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(expeditionLogService.createLog(request));
    }

    // ===============================================
    // GET ALL LOGS (PAGINATED + OPTIONAL MODULE TAB)
    // ===============================================
    @GetMapping
    public ResponseEntity<Page<ExpeditionLogResponse>> getLogs(
            @RequestParam(required = false) ActivityCategory category,
            @PageableDefault(page = 0, size = 15, sort = "timestamp") Pageable pageable) {

        if (category != null) {
            return ResponseEntity.ok(expeditionLogService.getLogsByCategory(category, pageable));
        }
        return ResponseEntity.ok(expeditionLogService.getAllLogs(pageable));
    }

    // ==========================
    // GET LOGS BY BOOKING ID
    // ==========================
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<ExpeditionLogResponse>> getLogsByBooking(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(expeditionLogService.getLogsByBooking(bookingId));
    }
}