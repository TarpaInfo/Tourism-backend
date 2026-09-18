package com.tarpa.tourism.invoice.controller;

import com.tarpa.tourism.booking.entity.Booking;
import com.tarpa.tourism.booking.repository.BookingRepository;
import com.tarpa.tourism.exception.ResourceNotFoundException;
import com.tarpa.tourism.invoice.service.PdfInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class InvoiceController {

    private final BookingRepository bookingRepository;
    private final PdfInvoiceService pdfInvoiceService;

    @GetMapping("/{bookingId}/invoice")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found: " + bookingId));

        byte[] pdfBytes = pdfInvoiceService.generateInvoice(booking);

        // Tell the browser this is a PDF file that should be downloaded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Invoice-" + booking.getBookingCode() + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}