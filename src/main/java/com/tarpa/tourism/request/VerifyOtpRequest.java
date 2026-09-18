package com.tarpa.tourism.request;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String sessionToken;
    private String otpCode;
}