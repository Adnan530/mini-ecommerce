package com.ecommerce.notification_service.listener;

import com.ecommerce.notification_service.dto.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentProcessedListener {

    @RabbitListener(queues = "${rabbitmq.payment-processed.queue}")
    public void handlePaymentProcessed(PaymentEvent paymentEvent) {
        if (paymentEvent.isSuccess()) {
            log.info("Notification: Payment successful for Order {} - {}",
                    paymentEvent.getOrderId(), paymentEvent.getMessage());
        } else {
            log.warn("Notification: Payment failed for Order {} - {}",
                    paymentEvent.getOrderId(), paymentEvent.getMessage());
        }
    }
}

