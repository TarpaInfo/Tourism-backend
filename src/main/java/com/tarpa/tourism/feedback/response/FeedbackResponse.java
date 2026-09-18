package com.tarpa.tourism.feedback.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackResponse {
    private Long id;
    private Long bookingId;
    private String bookingCode;
    private String clientName;
    private String packageName;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
    private boolean approved;
}