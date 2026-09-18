package com.tarpa.tourism.permit.controller;

import com.tarpa.tourism.exception.ResourceNotFoundException;
import com.tarpa.tourism.permit.dto.PermitRequestDto;
import com.tarpa.tourism.permit.entity.Permit;
import com.tarpa.tourism.permit.model.PermitStatus;
import com.tarpa.tourism.permit.repository.PermitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permits")
@RequiredArgsConstructor
public class PermitController {

    private final PermitRepository permitRepository;

    @PostMapping
    public ResponseEntity<Permit> createPermit(@RequestBody PermitRequestDto dto) {
        Permit permit = Permit.builder()
                .bookingId(dto.getBookingId())
                .clientId(dto.getClientId())
                .permitType(dto.getPermitType())
                .governmentPermitNumber(dto.getGovernmentPermitNumber())
                .status(dto.getStatus() != null ? dto.getStatus() : PermitStatus.DRAFT)
                .feeInNpr(dto.getFeeInNpr())
                .issueDate(dto.getIssueDate())
                .expiryDate(dto.getExpiryDate())
                .documentUrl(dto.getDocumentUrl())
                .remarks(dto.getRemarks())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(permitRepository.save(permit));
    }

    @GetMapping
    public ResponseEntity<List<Permit>> getAllPermits() {
        return ResponseEntity.ok(permitRepository.findAll());
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<Permit>> getPermitsByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(permitRepository.findByBookingId(bookingId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Permit> updateStatus(
            @PathVariable Long id,
            @RequestParam PermitStatus status,
            @RequestParam(required = false) String governmentPermitNumber
    ) {
        Permit permit = permitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permit record not found with ID: " + id));

        permit.setStatus(status);
        if (governmentPermitNumber != null && !governmentPermitNumber.isBlank()) {
            permit.setGovernmentPermitNumber(governmentPermitNumber);
        }

        return ResponseEntity.ok(permitRepository.save(permit));
    }
}