package com.ecommerce.order_service.event;

import com.ecommerce.order_service.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusUpdateEvent {
    private UUID orderId;
    private OrderStatus status;
}
