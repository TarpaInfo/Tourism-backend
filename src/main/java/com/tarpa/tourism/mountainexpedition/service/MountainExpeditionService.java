package com.tarpa.tourism.mountainexpedition.service;

import com.tarpa.tourism.mountainexpedition.request.MountainExpeditionRequest;
import com.tarpa.tourism.mountainexpedition.response.MountainExpeditionResponse;

import java.util.List;

public interface MountainExpeditionService {

    MountainExpeditionResponse createMountainExpedition(
            MountainExpeditionRequest request
    );

    List<MountainExpeditionResponse> getAllMountainExpeditions();

    MountainExpeditionResponse getMountainExpeditionById(Long id);

    MountainExpeditionResponse updateMountainExpedition(
            Long id,
            MountainExpeditionRequest request
    );

    void deleteMountainExpedition(Long id);
}