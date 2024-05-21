package com.Yaktta.Disco.service;

import com.Yaktta.Disco.models.request.BrandSaveRequest;
import com.Yaktta.Disco.models.response.BrandResponse;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    public List<BrandResponse> findAll();
    public Optional<BrandResponse> findById(Long id);
    public BrandResponse save(BrandSaveRequest brand);
    public void delete(Long id);
}
