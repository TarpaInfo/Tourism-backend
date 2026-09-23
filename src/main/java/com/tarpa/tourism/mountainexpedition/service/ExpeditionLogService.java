package com.tarpa.tourism.mountainexpedition.service;

import com.tarpa.tourism.booking.entity.Booking;
import com.tarpa.tourism.booking.repository.BookingRepository;
import com.tarpa.tourism.email.service.EmailService;
import com.tarpa.tourism.mountainexpedition.request.ExpeditionLogRequest;
import com.tarpa.tourism.mountainexpedition.response.ExpeditionLogResponse;
import com.tarpa.tourism.mountainexpedition.entity.ExpeditionLog;
import com.tarpa.tourism.mountainexpedition.enums.ActivityCategory;
import com.tarpa.tourism.mountainexpedition.enums.IncidentSeverity;
import com.tarpa.tourism.mountainexpedition.repository.ExpeditionLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpeditionLogService {

    private final ExpeditionLogRepository expeditionLogRepository;
    private final BookingRepository bookingRepository;
    private final EmailService emailService;

    private static final String EMERGENCY_DISPATCH_EMAIL = "info@rabinepal.com.np";

    /**
     * Create a new field report or waypoint check-in across all 5 operational modules
     */
    @Transactional
    public ExpeditionLogResponse createLog(ExpeditionLogRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + request.getBookingId()));

        // Resolve Activity Category: User input -> Booking package link -> Fallback default
        ActivityCategory resolvedCategory = request.getActivityCategory();
        if (resolvedCategory == null) {
            resolvedCategory = resolveCategoryFromBooking(booking);
        }

        ExpeditionLog entry = ExpeditionLog.builder()
                .booking(booking)
                .activityCategory(resolvedCategory)
                .logType(request.getLogType())
                .severity(request.getSeverity())
                .locationName(request.getLocationName().trim())
                .altitudeMeters(request.getAltitudeMeters())
                .reportNotes(request.getReportNotes())
                .reportedBy(request.getReportedBy() != null && !request.getReportedBy().isBlank()
                        ? request.getReportedBy().trim()
                        : "Field Command")
                .heliRescueRequested(Boolean.TRUE.equals(request.getHeliRescueRequested()))
                .build();

        ExpeditionLog savedLog = expeditionLogRepository.save(entry);

        // Escalation trigger: Critical medical/AMS event or Emergency Heli request
        if (savedLog.getSeverity() == IncidentSeverity.CRITICAL_EMERGENCY
                || Boolean.TRUE.equals(savedLog.getHeliRescueRequested())) {
            dispatchEmergencyEscalation(savedLog, booking);
        }

        return mapToResponse(savedLog);
    }

    /**
     * Fetch paginated logs across all 5 activity lines
     */
    @Transactional(readOnly = true)
    public Page<ExpeditionLogResponse> getAllLogs(Pageable pageable) {
        return expeditionLogRepository.findAllByOrderByTimestampDesc(pageable)
                .map(this::mapToResponse);
    }

    /**
     * Fetch paginated logs filtered by specific operational module
     */
    @Transactional(readOnly = true)
    public Page<ExpeditionLogResponse> getLogsByCategory(ActivityCategory category, Pageable pageable) {
        return expeditionLogRepository.findByActivityCategoryOrderByTimestampDesc(category, pageable)
                .map(this::mapToResponse);
    }

    /**
     * Fetch complete historical telemetry for an individual booking
     */
    @Transactional(readOnly = true)
    public List<ExpeditionLogResponse> getLogsByBooking(Long bookingId) {
        return expeditionLogRepository.findByBookingIdOrderByTimestampDesc(bookingId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Fallback resolution to identify module from the linked TourPackage
     */
    private ActivityCategory resolveCategoryFromBooking(Booking booking) {
        if (booking.getTourPackage() != null && booking.getTourPackage().getPackageName() != null) {
            String pkgName = booking.getTourPackage().getPackageName().toUpperCase();
            if (pkgName.contains("HELI")) return ActivityCategory.HELI_TOUR;
            if (pkgName.contains("EXPEDITION") || pkgName.contains("EVEREST") || pkgName.contains("8000")) return ActivityCategory.EXPEDITION;
            if (pkgName.contains("CLIMBING") || pkgName.contains("PEAK") || pkgName.contains("MERA") || pkgName.contains("ISLAND")) return ActivityCategory.PEAK_CLIMBING;
            if (pkgName.contains("TOUR") || pkgName.contains("SAFARI") || pkgName.contains("HERITAGE") || pkgName.contains("CHITWAN")) return ActivityCategory.TOUR;
        }
        return ActivityCategory.TREKKING;
    }

    /**
     * Urgent email dispatcher for rescue teams and back-office ops
     */
    private void dispatchEmergencyEscalation(ExpeditionLog logEntry, Booking booking) {
        try {
            String clientName = booking.getClient() != null
                    ? booking.getClient().getFirstName() + " " + booking.getClient().getLastName()
                    : "Trekker Roster";

            String subject = "URGENT RESCUE ALERT: " + booking.getBookingCode() + " [" + logEntry.getActivityCategory() + "] at " + logEntry.getLocationName();

            String html = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 2px solid #ef4444; border-radius: 12px; background-color: #fef2f2;'>"
                    + "<h2 style='color: #dc2626; margin-top: 0;'>CRITICAL FIELD DISPATCH ALERT</h2>"
                    + "<p>An emergency waypoint alert has been triggered from the field:</p>"
                    + "<table style='width: 100%; text-align: left; border-collapse: collapse; font-size: 14px;'>"
                    + "<tr><td style='padding: 6px 0;'><strong>Booking Code:</strong></td><td>" + booking.getBookingCode() + "</td></tr>"
                    + "<tr><td style='padding: 6px 0;'><strong>Module:</strong></td><td>" + logEntry.getActivityCategory() + "</td></tr>"
                    + "<tr><td style='padding: 6px 0;'><strong>Client / Lead:</strong></td><td>" + clientName + "</td></tr>"
                    + "<tr><td style='padding: 6px 0;'><strong>Location:</strong></td><td>" + logEntry.getLocationName() + " (" + (logEntry.getAltitudeMeters() != null ? logEntry.getAltitudeMeters() + "m" : "Ground") + ")</td></tr>"
                    + "<tr><td style='padding: 6px 0;'><strong>Severity:</strong></td><td style='color: #dc2626; font-weight: bold;'>" + logEntry.getSeverity() + "</td></tr>"
                    + "<tr><td style='padding: 6px 0;'><strong>Heli Rescue Requested:</strong></td><td style='color: #b91c1c; font-weight: bold;'>" + (Boolean.TRUE.equals(logEntry.getHeliRescueRequested()) ? "YES - ESCALATE IMMEDIATELY" : "NO") + "</td></tr>"
                    + "<tr><td style='padding: 6px 0;'><strong>Reported By:</strong></td><td>" + logEntry.getReportedBy() + "</td></tr>"
                    + "</table>"
                    + "<div style='margin-top: 15px; padding: 12px; background-color: #ffffff; border: 1px solid #fca5a5; border-radius: 8px; font-size: 13px;'>"
                    + "<strong>Field Notes:</strong><br/>" + (logEntry.getReportNotes() != null ? logEntry.getReportNotes() : "No additional notes provided.")
                    + "</div>"
                    + "</div>";

            emailService.sendHtmlEmail(EMERGENCY_DISPATCH_EMAIL, subject, html);
        } catch (Exception ex) {
            log.error("Failed to transmit emergency dispatch email for {}: {}", booking.getBookingCode(), ex.getMessage());
        }
    }

    private ExpeditionLogResponse mapToResponse(ExpeditionLog entity) {
        String clientName = entity.getBooking().getClient() != null
                ? entity.getBooking().getClient().getFirstName() + " " + entity.getBooking().getClient().getLastName()
                : "Lead Client";

        String routeName = entity.getBooking().getTourPackage() != null
                ? entity.getBooking().getTourPackage().getPackageName()
                : "Operational Itinerary";

        return ExpeditionLogResponse.builder()
                .id(entity.getId())
                .bookingId(entity.getBooking().getId())
                .bookingCode(entity.getBooking().getBookingCode())
                .clientName(clientName)
                .routeName(routeName)
                .activityCategory(entity.getActivityCategory())
                .logType(entity.getLogType())
                .severity(entity.getSeverity())
                .locationName(entity.getLocationName())
                .altitudeMeters(entity.getAltitudeMeters())
                .reportNotes(entity.getReportNotes())
                .reportedBy(entity.getReportedBy())
                .heliRescueRequested(entity.getHeliRescueRequested())
                .timestamp(entity.getTimestamp())
                .build();
    }
}