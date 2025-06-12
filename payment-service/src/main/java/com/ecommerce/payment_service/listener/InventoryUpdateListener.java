package com.ecommerce.payment_service.listener;

import com.ecommerce.payment_service.config.RabbitMQProperties;
import com.ecommerce.payment_service.event.InventoryUpdateEvent;
import com.ecommerce.payment_service.event.PaymentEvent;
import com.ecommerce.payment_service.service.impl.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryUpdateListener {
    private final PaymentService paymentService;
    private final AmqpTemplate amqpTemplate;
    private final RabbitMQProperties rabbitMQProperties;

    @RabbitListener(queues = "${rabbitmq.inventory-updated-queue}")
    public void handleInventoryUpdate(InventoryUpdateEvent inventoryUpdateEvent){
        log.info("Received InventoryUpdate Event : {}", inventoryUpdateEvent);

        if(!inventoryUpdateEvent.getSuccess()){
            log.warn("Inventory update failed for order {} : {}",
                    inventoryUpdateEvent.getOrderId(),inventoryUpdateEvent.getMessage());
            return;
        }

        PaymentEvent paymentEvent=paymentService.processPayment(inventoryUpdateEvent.getOrderId());
        amqpTemplate.convertAndSend(
                rabbitMQProperties.getPaymentProcessedQueue(),paymentEvent
        );
        log.info("Published Payment Event : {}", paymentEvent);
    }
}
