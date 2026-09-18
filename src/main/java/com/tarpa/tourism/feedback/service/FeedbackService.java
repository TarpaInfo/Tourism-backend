package com.tarpa.tourism.feedback.service;

import com.tarpa.tourism.feedback.request.FeedbackRequest;
import com.tarpa.tourism.feedback.response.FeedbackResponse;

import java.util.List;

public interface FeedbackService {

    FeedbackResponse submitFeedback(FeedbackRequest request);

    FeedbackResponse getFeedbackByBookingId(Long bookingId);

    List<FeedbackResponse> getApprovedTestimonials();

    void approveFeedback(Long feedbackId);
}