package com.tarpa.tourism.logistics.controller;

import com.tarpa.tourism.exception.ResourceNotFoundException;
import com.tarpa.tourism.logistics.dto.StaffRequestDto;
import com.tarpa.tourism.logistics.dto.TripAssignmentRequestDto;
import com.tarpa.tourism.logistics.entity.Staff;
import com.tarpa.tourism.logistics.entity.TripAssignment;
import com.tarpa.tourism.logistics.repository.StaffRepository;
import com.tarpa.tourism.logistics.repository.TripAssignmentRepository;
import com.tarpa.tourism.logistics.service.LogisticsNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logistics")
@RequiredArgsConstructor
public class LogisticsController {

    private final StaffRepository staffRepository;
    private final TripAssignmentRepository tripAssignmentRepository;
    private final LogisticsNotificationService notificationService;

    // --- STAFF ENDPOINTS ---

    @PostMapping("/staff")
    public ResponseEntity<Staff> createStaff(@RequestBody StaffRequestDto dto) {
        Staff staff = Staff.builder()
                .fullName(dto.getFullName())
                .role(dto.getRole())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .licenseNumber(dto.getLicenseNumber())
                .active(true)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(staffRepository.save(staff));
    }

    @PostMapping("/briefings/dispatch")
    public ResponseEntity<String> triggerBriefingDispatch() {
        int dispatchedCount = notificationService.dispatchPendingBriefings();
        return ResponseEntity.ok("Dispatched briefings for " + dispatchedCount + " assignment(s).");
    }

    @GetMapping("/staff")
    public ResponseEntity<List<Staff>> getAllStaff() {
        return ResponseEntity.ok(staffRepository.findAll());
    }

    // --- ASSIGNMENT ENDPOINTS ---

    @PostMapping("/assignments")
    public ResponseEntity<TripAssignment> assignStaffToTrip(@RequestBody TripAssignmentRequestDto dto) {
        Staff staff = staffRepository.findById(dto.getStaffId())
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with ID: " + dto.getStaffId()));

        TripAssignment assignment = TripAssignment.builder()
                .bookingId(dto.getBookingId())
                .staff(staff)
                .pickupLocation(dto.getPickupLocation())
                .vehicleDetails(dto.getVehicleDetails())
                .operationalNotes(dto.getOperationalNotes())
                .briefingSent(false)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(tripAssignmentRepository.save(assignment));
    }

    @GetMapping("/assignments/booking/{bookingId}")
    public ResponseEntity<List<TripAssignment>> getAssignmentsByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(tripAssignmentRepository.findByBookingId(bookingId));
    }

    @GetMapping("/assignments/staff/{staffId}")
    public ResponseEntity<List<TripAssignment>> getAssignmentsByStaff(@PathVariable Long staffId) {
        return ResponseEntity.ok(tripAssignmentRepository.findByStaffId(staffId));
    }
}