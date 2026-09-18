package com.tarpa.tourism.hotel.service.impl;

import com.tarpa.tourism.booking.entity.Booking;
import com.tarpa.tourism.booking.repository.BookingRepository;
import com.tarpa.tourism.exception.DuplicateResourceException;
import com.tarpa.tourism.exception.ResourceNotFoundException;
import com.tarpa.tourism.hotel.entity.HotelBooking;
import com.tarpa.tourism.hotel.repository.HotelBookingRepository;
import com.tarpa.tourism.hotel.request.HotelBookingRequest;
import com.tarpa.tourism.hotel.response.HotelBookingResponse;
import com.tarpa.tourism.hotel.service.HotelBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.tarpa.tourism.alert.request.AlertRequest;
import com.tarpa.tourism.alert.service.AlertService;
import com.tarpa.tourism.constant.AlertType;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelBookingServiceImpl implements HotelBookingService {

    private final HotelBookingRepository hotelBookingRepository;
    private final BookingRepository bookingRepository;
    private final AlertService alertService;

    // ==========================
    // CREATE
    // ==========================

    @Override
    public HotelBookingResponse createHotelBooking(
            HotelBookingRequest request) {

        if (hotelBookingRepository.existsByHotelBookingCode(
                request.getHotelBookingCode())) {

            throw new DuplicateResourceException(
                    "Hotel booking code already exists: "
                            + request.getHotelBookingCode()
            );
        }

        Booking booking = bookingRepository.findById(
                request.getBookingId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Booking not found with id: "
                                + request.getBookingId()
                )
        );

        HotelBooking hotelBooking = HotelBooking.builder()
                .hotelBookingCode(request.getHotelBookingCode())
                .booking(booking)
                .hotelName(request.getHotelName())
                .hotelAddress(request.getHotelAddress())
                .city(request.getCity())
                .country(request.getCountry())
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .numberOfNights(request.getNumberOfNights())
                .roomType(request.getRoomType())
                .numberOfRooms(request.getNumberOfRooms())
                .numberOfGuests(request.getNumberOfGuests())
                .mealPlan(request.getMealPlan())
                .totalAmount(request.getTotalAmount())
                .currency(request.getCurrency())
                .status(request.getStatus() != null
                        ? request.getStatus()
                        : "PENDING")
                .specialRequest(request.getSpecialRequest())
                .active(request.getActive() != null
                        ? request.getActive()
                        : true)
                .build();

        HotelBooking saved =
                hotelBookingRepository.save(hotelBooking);

        return mapToResponse(saved);
    }

    // ==========================
    // GET ALL
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<HotelBookingResponse> getAllHotelBookings() {

        return hotelBookingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================
    // GET BY ID
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public HotelBookingResponse getHotelBookingById(Long id) {

        HotelBooking hotelBooking =
                hotelBookingRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Hotel booking not found with id: "
                                                + id
                                )
                        );

        return mapToResponse(hotelBooking);
    }

    // ==========================
    // GET BY CODE
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public HotelBookingResponse getHotelBookingByCode(
            String hotelBookingCode) {

        HotelBooking hotelBooking =
                hotelBookingRepository
                        .findByHotelBookingCode(hotelBookingCode)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Hotel booking not found with code: "
                                                + hotelBookingCode
                                )
                        );

        return mapToResponse(hotelBooking);
    }

    // ==========================
    // GET BY BOOKING ID
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<HotelBookingResponse> getHotelBookingsByBookingId(
            Long bookingId) {

        // Verify booking exists
        bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found with id: "
                                        + bookingId
                        )
                );

        return hotelBookingRepository
                .findByBookingId(bookingId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================
    // UPDATE
    // ==========================

    @Override
    public HotelBookingResponse updateHotelBooking(
            Long id,
            HotelBookingRequest request) {

        HotelBooking hotelBooking =
                hotelBookingRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Hotel booking not found with id: "
                                                + id
                                )
                        );

        String previousStatus = hotelBooking.getStatus();

        if (!hotelBooking.getHotelBookingCode()
                .equals(request.getHotelBookingCode())) {

            if (hotelBookingRepository.existsByHotelBookingCode(
                    request.getHotelBookingCode())) {

                throw new DuplicateResourceException(
                        "Hotel booking code already exists: "
                                + request.getHotelBookingCode()
                );
            }
        }

        Booking booking = bookingRepository.findById(
                request.getBookingId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Booking not found with id: "
                                + request.getBookingId()
                )
        );

        hotelBooking.setHotelBookingCode(
                request.getHotelBookingCode()
        );
        hotelBooking.setBooking(booking);
        hotelBooking.setHotelName(request.getHotelName());
        hotelBooking.setHotelAddress(request.getHotelAddress());
        hotelBooking.setCity(request.getCity());
        hotelBooking.setCountry(request.getCountry());
        hotelBooking.setCheckInDate(request.getCheckInDate());
        hotelBooking.setCheckOutDate(request.getCheckOutDate());
        hotelBooking.setNumberOfNights(request.getNumberOfNights());
        hotelBooking.setRoomType(request.getRoomType());
        hotelBooking.setNumberOfRooms(request.getNumberOfRooms());
        hotelBooking.setNumberOfGuests(request.getNumberOfGuests());
        hotelBooking.setMealPlan(request.getMealPlan());
        hotelBooking.setTotalAmount(request.getTotalAmount());
        hotelBooking.setCurrency(request.getCurrency());
        hotelBooking.setStatus(request.getStatus());
        hotelBooking.setSpecialRequest(request.getSpecialRequest());

        if (request.getActive() != null) {
            hotelBooking.setActive(request.getActive());
        }

        HotelBooking updated =
                hotelBookingRepository.save(hotelBooking);

        if (!"CONFIRMED".equalsIgnoreCase(previousStatus)
                && "CONFIRMED".equalsIgnoreCase(updated.getStatus())) {

            AlertRequest alertRequest = AlertRequest.builder()
                    .alertCode("ALT-HTL-" + updated.getHotelBookingCode())
                    .bookingId(updated.getBooking().getId()) // Links to main booking for CC/Client email
                    .type(AlertType.HOTEL_BOOKING)
                    .title("Hotel Booking Confirmed")
                    .message("Dear " + updated.getBooking().getClient().getFirstName()
                            + ", your stay at " + updated.getHotelName()
                            + " in " + updated.getCity()
                            + " has been confirmed. Check-in: " + updated.getCheckInDate()
                            + ", Check-out: " + updated.getCheckOutDate() + ".")
                    .scheduledAt(LocalDateTime.now()) // Send confirmation immediately
                    .status("PENDING")
                    .active(true)
                    .build();

            try {
                alertService.createAlert(alertRequest);
            } catch (Exception e) {
                System.out.println("Failed to create alert for Hotel Booking: " + updated.getHotelBookingCode());
            }
        }

        return mapToResponse(updated);
    }

    // ==========================
    // DELETE
    // ==========================

    @Override
    public void deleteHotelBooking(Long id) {

        HotelBooking hotelBooking =
                hotelBookingRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Hotel booking not found with id: "
                                                + id
                                )
                        );

        hotelBookingRepository.delete(hotelBooking);
    }

    // ==========================
    // MAPPER
    // ==========================

    private HotelBookingResponse mapToResponse(
            HotelBooking hotelBooking) {

        Booking booking = hotelBooking.getBooking();

        return HotelBookingResponse.builder()
                .id(hotelBooking.getId())
                .hotelBookingCode(
                        hotelBooking.getHotelBookingCode()
                )
                .bookingId(booking.getId())
                .bookingCode(booking.getBookingCode())
                .hotelName(hotelBooking.getHotelName())
                .hotelAddress(hotelBooking.getHotelAddress())
                .city(hotelBooking.getCity())
                .country(hotelBooking.getCountry())
                .checkInDate(hotelBooking.getCheckInDate())
                .checkOutDate(hotelBooking.getCheckOutDate())
                .numberOfNights(
                        hotelBooking.getNumberOfNights()
                )
                .roomType(hotelBooking.getRoomType())
                .numberOfRooms(
                        hotelBooking.getNumberOfRooms()
                )
                .numberOfGuests(
                        hotelBooking.getNumberOfGuests()
                )
                .mealPlan(hotelBooking.getMealPlan())
                .totalAmount(hotelBooking.getTotalAmount())
                .currency(hotelBooking.getCurrency())
                .status(hotelBooking.getStatus())
                .specialRequest(
                        hotelBooking.getSpecialRequest()
                )
                .active(hotelBooking.getActive())
                .createdAt(hotelBooking.getCreatedAt())
                .updatedAt(hotelBooking.getUpdatedAt())
                .build();
    }
}