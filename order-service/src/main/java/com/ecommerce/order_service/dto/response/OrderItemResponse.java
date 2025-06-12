package com.ecommerce.order_service.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderItemResponse {
    private UUID id;
    private UUID productId;
    private Integer quantity;
    private BigDecimal price;
}
