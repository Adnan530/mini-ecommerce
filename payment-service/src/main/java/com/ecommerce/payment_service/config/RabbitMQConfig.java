package com.ecommerce.payment_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final RabbitMQProperties properties;

    @Bean
    public Queue inventoryUpdatedQueue() {
        return new Queue(properties.getInventoryUpdatedQueue(), true);
    }

    @Bean
    public Queue paymentProcessedQueue() {
        return new Queue(properties.getPaymentProcessedQueue(), true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(properties.getExchange());
    }

    @Bean
    public Binding bindingInventoryUpdated() {
        return BindingBuilder
                .bind(inventoryUpdatedQueue())
                .to(exchange())
                .with(properties.getInventoryUpdatedQueue());
    }

    @Bean
    public Binding bindingPaymentProcessed() {
        return BindingBuilder
                .bind(paymentProcessedQueue())
                .to(exchange())
                .with(properties.getPaymentProcessedQueue());
    }
}
