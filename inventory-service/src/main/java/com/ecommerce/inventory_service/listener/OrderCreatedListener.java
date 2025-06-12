package com.ecommerce.inventory_service.listener;


import com.ecommerce.inventory_service.config.RabbitMQProperties;
import com.ecommerce.inventory_service.entity.Inventory;
import com.ecommerce.inventory_service.enums.OrderStatus;
import com.ecommerce.inventory_service.event.InventoryUpdateEvent;
import com.ecommerce.inventory_service.event.OrderCreatedEvent;
import com.ecommerce.inventory_service.event.OrderItem;
import com.ecommerce.inventory_service.event.OrderStatusUpdateEvent;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedListener {

    private final InventoryRepository inventoryRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties rabbitMQProperties;

    @RabbitListener(queues = "#{rabbitMQProperties.orderCreated}")
    public void handleOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        log.info(" Received OrderCreatedEvent : {}", orderCreatedEvent);

        boolean allAvailable = true;
        StringBuilder message = new StringBuilder();

        // 1. Check inventory availability
        for (OrderItem item : orderCreatedEvent.getItems()) {
            Inventory inventory = inventoryRepository.findByProductId(item.getProductId()).orElse(null);

            if (inventory == null || inventory.getAvailableQuantity() < item.getQuantity()) {
                allAvailable = false;
                message.append(" Insufficient stock for productId ")
                        .append(item.getProductId()).append(". ");
            }
        }

        // 2. Reserve inventory if available
        if (allAvailable) {
            for (OrderItem item : orderCreatedEvent.getItems()) {
                Inventory inventory = inventoryRepository.findByProductId(item.getProductId()).get();
                inventory.setAvailableQuantity(inventory.getAvailableQuantity() - item.getQuantity());
                inventory.setReservedQuantity(inventory.getReservedQuantity() + item.getQuantity());
                inventory.setLastModifiedDate(LocalDateTime.now());
                inventoryRepository.save(inventory);
            }
        }

        // 3. Publish inventory update event
        InventoryUpdateEvent updateEvent = InventoryUpdateEvent.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .success(allAvailable)
                .message(allAvailable ? "Inventory reserved successfully." : message.toString())
                .build();

        rabbitTemplate.convertAndSend(
                rabbitMQProperties.getInventoryUpdated(),
                updateEvent
        );

        log.info(" Published InventoryUpdateEvent: {}", updateEvent);

        // 4. If inventory was reserved, also notify OrderService to set status to PENDING
        if (allAvailable) {
            OrderStatusUpdateEvent statusUpdateEvent = OrderStatusUpdateEvent.builder()
                    .orderId(orderCreatedEvent.getOrderId())
                    .status(OrderStatus.COMPLETED)
                    .build();

            rabbitTemplate.convertAndSend(
                    rabbitMQProperties.getOrderStatusUpdated(),
                    statusUpdateEvent
            );

            log.info(" Published OrderStatusUpdateEvent: {}", statusUpdateEvent);
        }
    }
}
