package com.tarpa.tourism.feedback.controller;

import com.tarpa.tourism.feedback.request.FeedbackRequest;
import com.tarpa.tourism.feedback.response.FeedbackResponse;
import com.tarpa.tourism.feedback.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<FeedbackResponse> submitFeedback(@Valid @RequestBody FeedbackRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.submitFeedback(request));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<FeedbackResponse> getFeedbackByBookingId(@PathVariable Long bookingId) {
        return ResponseEntity.ok(feedbackService.getFeedbackByBookingId(bookingId));
    }

    @GetMapping("/testimonials")
    public ResponseEntity<List<FeedbackResponse>> getApprovedTestimonials() {
        return ResponseEntity.ok(feedbackService.getApprovedTestimonials());
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<Void> approveFeedback(@PathVariable Long id) {
        feedbackService.approveFeedback(id);
        return ResponseEntity.noContent().build();
    }
}