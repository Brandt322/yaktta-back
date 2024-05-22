package com.Yaktta.Disco.service;

import com.Yaktta.Disco.models.entities.Product;
import com.Yaktta.Disco.models.request.ProductSaveRequest;
import com.Yaktta.Disco.models.response.ProductResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductResponse> findAll();
    Optional<ProductResponse> findById(Long id);
    ProductResponse save(ProductSaveRequest product);
    Product edit(Long id);
    Product delete(Long id);
}
