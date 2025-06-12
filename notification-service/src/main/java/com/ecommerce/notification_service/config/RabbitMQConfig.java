package com.ecommerce.notification_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final RabbitMQProperties properties;

    @Bean
    public Queue paymentProcessedQueue() {
        return new Queue(properties.getPaymentProcessed().getQueue(), true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(properties.getExchange());
    }

    @Bean
    public Binding bindingPaymentProcessed() {
        return BindingBuilder
                .bind(paymentProcessedQueue())
                .to(exchange())
                .with(properties.getPaymentProcessed().getQueue());
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}

