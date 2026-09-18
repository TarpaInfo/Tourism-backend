package com.tarpa.tourism.logistics.service;

import com.tarpa.tourism.logistics.entity.TripAssignment;
import com.tarpa.tourism.logistics.repository.TripAssignmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogisticsNotificationService {

    private final TripAssignmentRepository assignmentRepository;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${tourism.email.cc:}")
    private String operationsCcEmail;

    @Transactional
    public int dispatchPendingBriefings() {
        List<TripAssignment> pendingAssignments = assignmentRepository.findByBriefingSentFalse();

        if (pendingAssignments.isEmpty()) {
            log.info("Logistics dispatch: No pending briefings found.");
            return 0;
        }

        int successCount = 0;
        for (TripAssignment assignment : pendingAssignments) {
            boolean sent = sendBriefingEmail(assignment);
            if (sent) {
                assignment.setBriefingSent(true);
                assignmentRepository.save(assignment);
                successCount++;
            }
        }

        log.info("Dispatched {} operational briefings out of {}.", successCount, pendingAssignments.size());
        return successCount;
    }

    private boolean sendBriefingEmail(TripAssignment assignment) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail);
            message.setTo(assignment.getStaff().getEmail());

            if (operationsCcEmail != null && !operationsCcEmail.isBlank()) {
                message.setCc(operationsCcEmail);
            }

            message.setSubject("OPERATIONAL BRIEFING: Trip Assignment # " + assignment.getId() + " (Booking #" + assignment.getBookingId() + ")");

            String formattedTime = assignment.getAssignedAt() != null
                    ? assignment.getAssignedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    : "Immediate";

            String emailBody = String.format(
                    "Dear %s,\n\n" +
                            "You have been assigned as the field logistics lead for an upcoming itinerary.\n\n" +
                            "--- TRIP & DISPATCH DETAILS ---\n" +
                            "Assignment ID     : %d\n" +
                            "Booking ID        : %d\n" +
                            "Assigned Role     : %s\n" +
                            "Pickup Location   : %s\n" +
                            "Assigned Vehicle  : %s\n" +
                            "Assigned Time     : %s\n\n" +
                            "--- OPERATIONAL NOTES & REQUIREMENTS ---\n" +
                            "%s\n\n" +
                            "Please verify your equipment, ensure guest permits are in order, and confirm receipt of this briefing.\n\n" +
                            "Tarpa Tourism & Logistics Operations Team\nKathmandu, Nepal",
                    assignment.getStaff().getFullName(),
                    assignment.getId(),
                    assignment.getBookingId(),
                    assignment.getStaff().getRole(),
                    assignment.getPickupLocation(),
                    assignment.getVehicleDetails(),
                    formattedTime,
                    assignment.getOperationalNotes() != null ? assignment.getOperationalNotes() : "Standard operational guidelines apply."
            );

            message.setText(emailBody);
            mailSender.send(message);

            log.info("Email sent to staff: {} (Cc: {})", assignment.getStaff().getEmail(), operationsCcEmail);
            return true;
        } catch (Exception ex) {
            log.error("Failed to send briefing email for Assignment #{}: {}", assignment.getId(), ex.getMessage(), ex);
            return false;
        }
    }
}