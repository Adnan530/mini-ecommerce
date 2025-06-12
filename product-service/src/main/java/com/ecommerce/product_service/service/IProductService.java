package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IProductService {
    ProductResponse create(ProductRequest request);
    ProductResponse update(UUID id, ProductRequest request);
    void deleteById(UUID id);
    ProductResponse getById(UUID id);
    List<ProductResponse> getAll();
    Page<ProductResponse> getProducts(String name, String category, Boolean available, Pageable pageable);
}
