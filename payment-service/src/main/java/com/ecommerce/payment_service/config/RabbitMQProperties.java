package com.ecommerce.payment_service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
@Data
public class RabbitMQProperties {
    private String paymentProcessedQueue;
    private String inventoryUpdatedQueue;
    private String exchange;
}
