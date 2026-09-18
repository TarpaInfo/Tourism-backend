package com.tarpa.tourism.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpChallengeResponse {

    private String status;         // "OTP_REQUIRED"
    private String sessionToken;   // Tracking UUID
    private String maskedEmail;    // e.g., su****@gmail.com
    private String maskedPhone;    // e.g., ****4521
}