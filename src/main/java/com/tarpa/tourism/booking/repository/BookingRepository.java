package com.tarpa.tourism.booking.repository;

import com.tarpa.tourism.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingCode(String bookingCode);

    boolean existsByBookingCode(String bookingCode);

    boolean existsByClientIdAndTourPackageIdAndTravelDate(
            Long clientId,
            Long tourPackageId,
            java.time.LocalDate travelDate
    );

    List<Booking> findByBookingStatus(String bookingStatus);
}