package com.Yaktta.Disco.service;

import com.Yaktta.Disco.models.response.BrandResponse;
import com.Yaktta.Disco.models.response.OrderResponse;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderResponse> findAll();
    Optional<OrderResponse> findById(Long id);
    List<OrderResponse> findByClientId(Long clientId);
    void updateOrderStatus(Long id, boolean status);
}
