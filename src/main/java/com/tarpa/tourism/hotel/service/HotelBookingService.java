package com.tarpa.tourism.hotel.service;

import com.tarpa.tourism.hotel.request.HotelBookingRequest;
import com.tarpa.tourism.hotel.response.HotelBookingResponse;

import java.util.List;

public interface HotelBookingService {

    HotelBookingResponse createHotelBooking(HotelBookingRequest request);

    List<HotelBookingResponse> getAllHotelBookings();

    HotelBookingResponse getHotelBookingById(Long id);

    HotelBookingResponse getHotelBookingByCode(String hotelBookingCode);

    List<HotelBookingResponse> getHotelBookingsByBookingId(Long bookingId);

    HotelBookingResponse updateHotelBooking(
            Long id,
            HotelBookingRequest request
    );

    void deleteHotelBooking(Long id);
}