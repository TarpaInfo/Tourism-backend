package com.tarpa.tourism.feedback.service.impl;

import com.tarpa.tourism.booking.entity.Booking;
import com.tarpa.tourism.booking.repository.BookingRepository;
import com.tarpa.tourism.exception.ResourceNotFoundException;
import com.tarpa.tourism.feedback.request.FeedbackRequest;
import com.tarpa.tourism.feedback.response.FeedbackResponse;
import com.tarpa.tourism.feedback.entity.Feedback;
import com.tarpa.tourism.feedback.repository.FeedbackRepository;
import com.tarpa.tourism.feedback.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public FeedbackResponse submitFeedback(FeedbackRequest request) {
        if (feedbackRepository.existsByBookingId(request.getBookingId())) {
            throw new IllegalStateException("Feedback has already been submitted for this booking.");
        }

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found: " + request.getBookingId()));

        Feedback feedback = Feedback.builder()
                .booking(booking)
                .rating(request.getRating())
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .approved(false) // Default to false pending review
                .build();

        Feedback saved = feedbackRepository.save(feedback);
        return mapToResponse(saved);
    }

    @Override
    public FeedbackResponse getFeedbackByBookingId(Long bookingId) {
        Feedback feedback = feedbackRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("No feedback found for booking: " + bookingId));
        return mapToResponse(feedback);
    }

    @Override
    public List<FeedbackResponse> getApprovedTestimonials() {
        return feedbackRepository.findByApprovedTrueOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void approveFeedback(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found: " + feedbackId));
        feedback.setApproved(true);
        feedbackRepository.save(feedback);
    }

    private FeedbackResponse mapToResponse(Feedback feedback) {
        Booking booking = feedback.getBooking();
        return FeedbackResponse.builder()
                .id(feedback.getId())
                .bookingId(booking.getId())
                .bookingCode(booking.getBookingCode())
                .clientName(booking.getClient().getFirstName() + " " + booking.getClient().getLastName())
                .packageName(booking.getTourPackage() != null ? booking.getTourPackage().getPackageName() : "Custom Tour")
                .rating(feedback.getRating())
                .comment(feedback.getComment())
                .createdAt(feedback.getCreatedAt())
                .approved(feedback.isApproved())
                .build();
    }
}