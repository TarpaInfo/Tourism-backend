package com.tarpa.tourism.trekking.service;

import com.tarpa.tourism.trekking.request.TrekkingRequest;
import com.tarpa.tourism.trekking.response.TrekkingResponse;

import java.util.List;

public interface TrekkingService {

    TrekkingResponse createTrekking(TrekkingRequest request);

    List<TrekkingResponse> getAllTrekkings();

    TrekkingResponse getTrekkingById(Long id);

    TrekkingResponse updateTrekking(Long id, TrekkingRequest request);

    void deleteTrekking(Long id);
}