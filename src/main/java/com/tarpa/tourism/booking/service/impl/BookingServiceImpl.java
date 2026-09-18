package com.tarpa.tourism.booking.service;

import com.tarpa.tourism.booking.entity.Booking;
import com.tarpa.tourism.booking.repository.BookingRepository;
import com.tarpa.tourism.booking.request.BookingRequest;
import com.tarpa.tourism.booking.response.BookingResponse;
import com.tarpa.tourism.entity.Client;
import com.tarpa.tourism.repository.ClientRepository;
import com.tarpa.tourism.email.service.EmailService;
import com.tarpa.tourism.tour.entity.TourPackage;
import com.tarpa.tourism.tour.repository.TourPackageRepository;
import com.tarpa.tourism.util.EmailTemplateBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ClientRepository clientRepository;
    private final TourPackageRepository tourPackageRepository;
    private final EmailService emailService;

    private static final String INTERNAL_OPERATIONS_EMAIL = "info@rabinepal.com.np";

    // 1. Create Booking
    @Transactional
    @Override
    public BookingResponse createBooking(BookingRequest request) {
        boolean codeExists = bookingRepository.existsByBookingCode(request.getBookingCode());
        boolean tripExists = bookingRepository.existsByClientIdAndTourPackageIdAndTravelDate(
                request.getClientId(), request.getTourPackageId(), request.getTravelDate());
        // #region agent log
        try {
            String line = String.format(
                    "{\"sessionId\":\"c32bd2\",\"hypothesisId\":\"A\",\"location\":\"BookingServiceImpl.java:createBooking\",\"message\":\"createBooking duplicate checks\",\"data\":{\"bookingCode\":\"%s\",\"clientId\":%s,\"tourPackageId\":%s,\"travelDate\":\"%s\",\"codeExists\":%s,\"tripExists\":%s,\"status\":\"%s\"},\"timestamp\":%d}%n",
                    request.getBookingCode(),
                    request.getClientId(),
                    request.getTourPackageId(),
                    request.getTravelDate(),
                    codeExists,
                    tripExists,
                    request.getBookingStatus(),
                    System.currentTimeMillis());
            java.nio.file.Files.writeString(
                    java.nio.file.Path.of("C:\\Users\\NITRO V15\\Desktop\\Tourism Managment System\\tourism-management-system\\debug-c32bd2.log"),
                    line,
                    java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.APPEND);
        } catch (Exception ignored) {}
        // #endregion

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + request.getClientId()));

        TourPackage tourPackage = tourPackageRepository.findById(request.getTourPackageId())
                .orElseThrow(() -> new RuntimeException("Tour package not found with id: " + request.getTourPackageId()));

        Booking booking = Booking.builder()
                .bookingCode(request.getBookingCode())
                .client(client)
                .tourPackage(tourPackage)
                .travelDate(request.getTravelDate())
                .numberOfTravelers(request.getNumberOfTravelers())
                .totalAmount(request.getTotalAmount())
                .currency(request.getCurrency())
                .bookingStatus(request.getBookingStatus())
                .paymentStatus(request.getPaymentStatus())
                .specialRequest(request.getSpecialRequest())
                .vehicleDetails(request.getVehicleDetails())
                .active(true)
                .build();

        Booking savedBooking = bookingRepository.save(booking);
        // #region agent log
        try {
            String line = String.format(
                    "{\"sessionId\":\"c32bd2\",\"hypothesisId\":\"B\",\"location\":\"BookingServiceImpl.java:createBooking:saved\",\"message\":\"booking saved\",\"data\":{\"id\":%s,\"status\":\"%s\",\"willDispatchEmail\":%s},\"timestamp\":%d}%n",
                    savedBooking.getId(),
                    savedBooking.getBookingStatus(),
                    "CONFIRMED".equalsIgnoreCase(savedBooking.getBookingStatus()),
                    System.currentTimeMillis());
            java.nio.file.Files.writeString(
                    java.nio.file.Path.of("C:\\Users\\NITRO V15\\Desktop\\Tourism Managment System\\tourism-management-system\\debug-c32bd2.log"),
                    line,
                    java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.APPEND);
        } catch (Exception ignored) {}
        // #endregion

        if ("CONFIRMED".equalsIgnoreCase(savedBooking.getBookingStatus())) {
            dispatchBookingEmails(savedBooking, client, tourPackage);
        }

        return mapToResponse(savedBooking);
    }

    // 2. Get All Bookings
    @Transactional(readOnly = true)
    @Override
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 3. Get Booking By ID
    @Transactional(readOnly = true)
    @Override
    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        return mapToResponse(booking);
    }

    // 4. Get Booking By Reference Code (e.g. TRK-2026-01)
    @Transactional(readOnly = true)
    @Override
    public BookingResponse getBookingByCode(String bookingCode) {
        Booking booking = bookingRepository.findByBookingCode(bookingCode)
                .orElseThrow(() -> new RuntimeException("Booking not found with code: " + bookingCode));
        return mapToResponse(booking);
    }

    // 5. Update Booking
    @Transactional
    @Override
    public BookingResponse updateBooking(Long id, BookingRequest request) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        String previousStatus = booking.getBookingStatus();

        booking.setTravelDate(request.getTravelDate());
        booking.setNumberOfTravelers(request.getNumberOfTravelers());
        booking.setTotalAmount(request.getTotalAmount());
        booking.setBookingStatus(request.getBookingStatus());
        booking.setPaymentStatus(request.getPaymentStatus());
        booking.setSpecialRequest(request.getSpecialRequest());
        booking.setVehicleDetails(request.getVehicleDetails());

        Booking updatedBooking = bookingRepository.save(booking);

        if (!"CONFIRMED".equalsIgnoreCase(previousStatus) && "CONFIRMED".equalsIgnoreCase(updatedBooking.getBookingStatus())) {
            dispatchBookingEmails(updatedBooking, updatedBooking.getClient(), updatedBooking.getTourPackage());
        }

        return mapToResponse(updatedBooking);
    }

    // 6. Delete Booking
    @Transactional
    @Override
    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        bookingRepository.delete(booking);
        log.info("Booking with ID {} deleted successfully", id);
    }

    /**
     * Helper to build and dispatch dual emails: one for the client, one for internal operations
     */
    private void dispatchBookingEmails(Booking booking, Client client, TourPackage pkg) {
        try {
            String clientName = "Valued Traveler";
            if (client != null && client.getFirstName() != null) {
                clientName = client.getFirstName() + (client.getLastName() != null ? " " + client.getLastName() : "");
            }

            String routeTitle = (pkg != null && pkg.getPackageName() != null)
                    ? pkg.getPackageName()
                    : "Himalayan Expedition Route";

            String travelDateStr = (booking.getTravelDate() != null)
                    ? booking.getTravelDate().toString()
                    : "Scheduled";

            BigDecimal totalAmount = (booking.getTotalAmount() != null)
                    ? booking.getTotalAmount()
                    : BigDecimal.ZERO;

            String clientHtml = EmailTemplateBuilder.buildClientBookingConfirmation(
                    clientName,
                    booking.getBookingCode(),
                    routeTitle,
                    travelDateStr,
                    booking.getNumberOfTravelers(),
                    totalAmount,
                    booking.getCurrency(),
                    booking.getVehicleDetails(),
                    booking.getSpecialRequest()
            );

            if (client != null && client.getEmail() != null && !client.getEmail().isBlank()) {
                emailService.sendHtmlEmail(
                        client.getEmail(),
                        "Expedition Confirmation: " + booking.getBookingCode() + " - " + routeTitle,
                        clientHtml
                );
            }

            String clientEmail = (client != null && client.getEmail() != null) ? client.getEmail() : "N/A";
            String clientPhone = (client != null && client.getPhone() != null) ? client.getPhone() : "N/A";

            String staffHtml = EmailTemplateBuilder.buildStaffDispatchNotification(
                    booking.getBookingCode(),
                    clientName,
                    clientEmail,
                    clientPhone,
                    routeTitle,
                    travelDateStr,
                    booking.getNumberOfTravelers(),
                    booking.getVehicleDetails(),
                    booking.getSpecialRequest()
            );

            emailService.sendHtmlEmail(
                    INTERNAL_OPERATIONS_EMAIL,
                    "OPERATIONS DISPATCH: " + booking.getBookingCode() + " (" + clientName + ")",
                    staffHtml
            );

        } catch (Exception ex) {
            // #region agent log
            try {
                String line = String.format(
                        "{\"sessionId\":\"c32bd2\",\"hypothesisId\":\"C\",\"location\":\"BookingServiceImpl.java:dispatchBookingEmails\",\"message\":\"email compile/queue failed\",\"data\":{\"bookingCode\":\"%s\",\"exType\":\"%s\",\"exMessage\":\"%s\"},\"timestamp\":%d}%n",
                        booking.getBookingCode(),
                        ex.getClass().getName(),
                        String.valueOf(ex.getMessage()).replace("\"", "'"),
                        System.currentTimeMillis());
                java.nio.file.Files.writeString(
                        java.nio.file.Path.of("C:\\Users\\NITRO V15\\Desktop\\Tourism Managment System\\tourism-management-system\\debug-c32bd2.log"),
                        line,
                        java.nio.file.StandardOpenOption.CREATE,
                        java.nio.file.StandardOpenOption.APPEND);
            } catch (Exception ignored) {}
            // #endregion
            log.error("Failed to compile or queue booking emails for {}: {}", booking.getBookingCode(), ex.getMessage());
        }
    }

    private BookingResponse mapToResponse(Booking booking) {
        String clientFullName = "Valued Traveler";
        if (booking.getClient() != null && booking.getClient().getFirstName() != null) {
            clientFullName = booking.getClient().getFirstName() +
                    (booking.getClient().getLastName() != null ? " " + booking.getClient().getLastName() : "");
        }

        String packageName = booking.getTourPackage() != null ? booking.getTourPackage().getPackageName() : "Expedition Route";

        return BookingResponse.builder()
                .id(booking.getId())
                .bookingCode(booking.getBookingCode())
                .clientId(booking.getClient() != null ? booking.getClient().getId() : null)
                .clientName(clientFullName)
                .tourPackageId(booking.getTourPackage() != null ? booking.getTourPackage().getId() : null)
                .packageName(packageName)
                .travelDate(booking.getTravelDate())
                .numberOfTravelers(booking.getNumberOfTravelers())
                .totalAmount(booking.getTotalAmount())
                .currency(booking.getCurrency())
                .bookingStatus(booking.getBookingStatus())
                .paymentStatus(booking.getPaymentStatus())
                .specialRequest(booking.getSpecialRequest())
                .vehicleDetails(booking.getVehicleDetails())
                .active(booking.getActive())
                .build();
    }
}