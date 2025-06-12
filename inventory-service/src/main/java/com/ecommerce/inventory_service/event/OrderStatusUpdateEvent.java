package com.ecommerce.inventory_service.event;

import com.ecommerce.inventory_service.enums.OrderStatus;
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
