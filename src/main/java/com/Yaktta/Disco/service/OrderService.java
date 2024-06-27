package com.Yaktta.Disco.service;

import com.Yaktta.Disco.models.response.BrandResponse;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<BrandResponse> findAll();
    Optional<BrandResponse> findById(Long id);
}
