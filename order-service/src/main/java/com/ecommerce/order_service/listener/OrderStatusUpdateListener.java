package com.ecommerce.order_service.listener;

import com.ecommerce.order_service.event.OrderStatusUpdateEvent;
import com.ecommerce.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderStatusUpdateListener {

    private final OrderRepository orderRepository;

    @RabbitListener(queues = "${rabbitmq.order-status.queue}")
    public void handleOrderStatusUpdate(OrderStatusUpdateEvent event) {
        log.info("Received OrderStatusUpdateEvent: {}", event);

        orderRepository.findById(event.getOrderId()).ifPresent(order -> {
            order.setStatus(event.getStatus());
            order.setLastModifiedDate(LocalDateTime.now());
            orderRepository.save(order);
            log.info("Order {} status updated to {}", event.getOrderId(), event.getStatus());
        });
    }
}
