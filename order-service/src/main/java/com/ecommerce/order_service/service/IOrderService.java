package com.ecommerce.order_service.service;

import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.OrderResponse;
import java.util.List;
import java.util.UUID;


public interface IOrderService {
    OrderResponse create(OrderRequest requestDTO);
    OrderResponse getById(UUID orderId);
    List<OrderResponse> getAll();
}

