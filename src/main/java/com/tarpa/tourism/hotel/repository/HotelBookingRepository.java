package com.tarpa.tourism.hotel.repository;

import com.tarpa.tourism.hotel.entity.HotelBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HotelBookingRepository
        extends JpaRepository<HotelBooking, Long> {

    boolean existsByHotelBookingCode(String hotelBookingCode);

    Optional<HotelBooking> findByHotelBookingCode(String hotelBookingCode);

    List<HotelBooking> findByBookingId(Long bookingId);

    List<HotelBooking> findByStatusAndCheckInDate(String status, java.time.LocalDate checkInDate);

    List<HotelBooking> findByCheckInDate(LocalDate checkInDate);

    List<HotelBooking> findByCheckOutDate(LocalDate checkOutDate);

}