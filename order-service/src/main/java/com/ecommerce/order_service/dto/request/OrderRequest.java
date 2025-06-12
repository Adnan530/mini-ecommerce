package com.ecommerce.order_service.dto.request;

import com.ecommerce.order_service.enums.OrderStatus;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private String customerId;
    private OrderStatus status;
    private List<OrderItemRequest> items;
}
