package com.tarpa.tourism.booking.controller;

import com.tarpa.tourism.booking.request.BookingRequest;
import com.tarpa.tourism.booking.response.BookingResponse;
import com.tarpa.tourism.booking.service.BookingService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // ==========================
    // CREATE
    // ==========================

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest request) {
        // #region agent log
        try {
            String payload = "{\"sessionId\":\"c32bd2\",\"hypothesisId\":\"A\",\"location\":\"BookingController.java:createBooking\",\"message\":\"POST /api/bookings\",\"data\":{\"bookingCode\":\"" + request.getBookingCode() + "\",\"clientId\":" + request.getClientId() + ",\"tourPackageId\":" + request.getTourPackageId() + ",\"travelDate\":\"" + request.getTravelDate() + "\",\"status\":\"" + request.getBookingStatus() + "\"},\"timestamp\":" + System.currentTimeMillis() + "}";
            java.nio.file.Files.writeString(
                    java.nio.file.Path.of("C:\\Users\\NITRO V15\\Desktop\\Tourism Managment System\\tourism-management-system\\debug-c32bd2.log"),
                    payload + System.lineSeparator(),
                    java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.APPEND);
            java.net.http.HttpClient.newHttpClient().send(
                    java.net.http.HttpRequest.newBuilder(java.net.URI.create("http://127.0.0.1:7696/ingest/17c6b8f8-e790-4d92-82c9-b52dbf9692c6"))
                            .header("Content-Type", "application/json")
                            .header("X-Debug-Session-Id", "c32bd2")
                            .POST(java.net.http.HttpRequest.BodyPublishers.ofString(payload))
                            .build(),
                    java.net.http.HttpResponse.BodyHandlers.discarding());
        } catch (Exception ignored) {}
        // #endregion
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(bookingService.createBooking(request));
        } catch (Exception ex) {
            // #region agent log
            try {
                String payload = "{\"sessionId\":\"c32bd2\",\"hypothesisId\":\"A\",\"location\":\"BookingController.java:createBooking:error\",\"message\":\"create failed\",\"data\":{\"exType\":\"" + ex.getClass().getName() + "\",\"exMessage\":\"" + String.valueOf(ex.getMessage()).replace("\"", "'") + "\"},\"timestamp\":" + System.currentTimeMillis() + "}";
                java.nio.file.Files.writeString(
                        java.nio.file.Path.of("C:\\Users\\NITRO V15\\Desktop\\Tourism Managment System\\tourism-management-system\\debug-c32bd2.log"),
                        payload + System.lineSeparator(),
                        java.nio.file.StandardOpenOption.CREATE,
                        java.nio.file.StandardOpenOption.APPEND);
                java.net.http.HttpClient.newHttpClient().send(
                        java.net.http.HttpRequest.newBuilder(java.net.URI.create("http://127.0.0.1:7696/ingest/17c6b8f8-e790-4d92-82c9-b52dbf9692c6"))
                                .header("Content-Type", "application/json")
                                .header("X-Debug-Session-Id", "c32bd2")
                                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(payload))
                                .build(),
                        java.net.http.HttpResponse.BodyHandlers.discarding());
            } catch (Exception ignored) {}
            // #endregion
            throw ex;
        }
    }

    // ==========================
    // GET ALL
    // ==========================

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {

        return ResponseEntity.ok(
                bookingService.getAllBookings()
        );
    }

    // ==========================
    // GET BY ID
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.getBookingById(id)
        );
    }

    // ==========================
    // GET BY BOOKING CODE
    // ==========================

    @GetMapping("/code/{bookingCode}")
    public ResponseEntity<BookingResponse> getBookingByCode(
            @PathVariable String bookingCode) {

        return ResponseEntity.ok(
                bookingService.getBookingByCode(bookingCode)
        );
    }

    // ==========================
    // UPDATE
    // ==========================

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> updateBooking(
            @PathVariable Long id,
            @Valid @RequestBody BookingRequest request) {

        return ResponseEntity.ok(
                bookingService.updateBooking(id, request)
        );
    }

    // ==========================
    // DELETE
    // ==========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(
            @PathVariable Long id) {

        bookingService.deleteBooking(id);

        return ResponseEntity.noContent().build();
    }
}