package com.ecommerce.notification_service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
@Data
public class RabbitMQProperties {
    private String exchange;
    private PaymentProcessed paymentProcessed;

    @Data
    public static class PaymentProcessed {
        private String queue;
    }
}

