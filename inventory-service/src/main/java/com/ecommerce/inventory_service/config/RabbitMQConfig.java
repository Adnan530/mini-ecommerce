package com.ecommerce.inventory_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final RabbitMQProperties rabbitMQProperties;

    @Bean
    public Queue orderCreatedQueue(){
        return new Queue(rabbitMQProperties.getOrderCreated());
    }

    @Bean
    public Queue inventoryUpdatedQueue(){
        return new Queue(rabbitMQProperties.getInventoryUpdated());
    }
}
