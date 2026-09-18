package com.tarpa.tourism.alert.service;

import com.tarpa.tourism.alert.request.AlertRequest;
import com.tarpa.tourism.alert.response.AlertResponse;

import java.util.List;

public interface AlertService {

    AlertResponse createAlert(AlertRequest request);

    List<AlertResponse> getAllAlerts();

    AlertResponse getAlertById(Long id);

    AlertResponse getAlertByCode(String alertCode);

    List<AlertResponse> getAlertsByBookingId(Long bookingId);

    List<AlertResponse> getAlertsByStatus(String status);

    List<AlertResponse> getAlertsByType(String type);

    AlertResponse updateAlert(Long id, AlertRequest request);

    void deleteAlert(Long id);


}