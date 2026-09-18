package com.tarpa.tourism.booking.service;

import com.tarpa.tourism.booking.request.BookingRequest;
import com.tarpa.tourism.booking.response.BookingResponse;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request);

    List<BookingResponse> getAllBookings();

    BookingResponse getBookingById(Long id);

    BookingResponse getBookingByCode(String bookingCode);

    BookingResponse updateBooking(Long id, BookingRequest request);

    void deleteBooking(Long id);
}