package com.Yaktta.Disco.service;

import com.Yaktta.Disco.models.request.BrandSaveRequest;
import com.Yaktta.Disco.models.response.BrandResponse;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    List<BrandResponse> findAll();
    Optional<BrandResponse> findById(Long id);
    BrandResponse save(BrandSaveRequest brand);
    String delete(Long id);
    BrandResponse edit(Long id,  BrandSaveRequest brandSaveRequest);
}
