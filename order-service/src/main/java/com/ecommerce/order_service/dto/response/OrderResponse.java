package com.ecommerce.order_service.dto.response;


import com.ecommerce.order_service.enums.OrderStatus;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class OrderResponse {
    private UUID id;
    private String customerId;
    private OrderStatus status;
    private List<OrderItemResponse> items;
}
