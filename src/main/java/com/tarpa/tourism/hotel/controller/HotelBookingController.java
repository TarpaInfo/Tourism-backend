package com.tarpa.tourism.hotel.controller;

import com.tarpa.tourism.hotel.request.HotelBookingRequest;
import com.tarpa.tourism.hotel.response.HotelBookingResponse;
import com.tarpa.tourism.hotel.service.HotelBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel-bookings")
@RequiredArgsConstructor
public class HotelBookingController {

    private final HotelBookingService hotelBookingService;

    // ==========================
    // CREATE
    // ==========================

    @PostMapping
    public ResponseEntity<HotelBookingResponse> createHotelBooking(
            @RequestBody HotelBookingRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(hotelBookingService.createHotelBooking(request));
    }

    // ==========================
    // GET ALL
    // ==========================

    @GetMapping
    public ResponseEntity<List<HotelBookingResponse>> getAllHotelBookings() {

        return ResponseEntity.ok(
                hotelBookingService.getAllHotelBookings()
        );
    }

    // ==========================
    // GET BY ID
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<HotelBookingResponse> getHotelBookingById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                hotelBookingService.getHotelBookingById(id)
        );
    }

    // ==========================
    // GET BY CODE
    // ==========================

    @GetMapping("/code/{hotelBookingCode}")
    public ResponseEntity<HotelBookingResponse> getHotelBookingByCode(
            @PathVariable String hotelBookingCode) {

        return ResponseEntity.ok(
                hotelBookingService.getHotelBookingByCode(
                        hotelBookingCode
                )
        );
    }

    // ==========================
    // GET BY BOOKING ID
    // ==========================

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<HotelBookingResponse>>
    getHotelBookingsByBookingId(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                hotelBookingService.getHotelBookingsByBookingId(
                        bookingId
                )
        );
    }

    // ==========================
    // UPDATE
    // ==========================

    @PutMapping("/{id}")
    public ResponseEntity<HotelBookingResponse> updateHotelBooking(
            @PathVariable Long id,
            @RequestBody HotelBookingRequest request) {

        return ResponseEntity.ok(
                hotelBookingService.updateHotelBooking(
                        id,
                        request
                )
        );
    }

    // ==========================
    // DELETE
    // ==========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotelBooking(
            @PathVariable Long id) {

        hotelBookingService.deleteHotelBooking(id);

        return ResponseEntity.noContent().build();
    }
}