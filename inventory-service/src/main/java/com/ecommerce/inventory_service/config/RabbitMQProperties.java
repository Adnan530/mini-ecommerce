package com.ecommerce.inventory_service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq.queues")
@Data
public class RabbitMQProperties {
    private String orderCreated;
    private String inventoryUpdated;
    private String orderStatusUpdated;
}
