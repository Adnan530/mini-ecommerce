package com.ecommerce.order_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.order.exchange}")
    private String orderExchangeName;

    @Value("${rabbitmq.order.queue}")
    private String orderQueueName;

    @Value("${rabbitmq.order.routingKey}")
    private String orderRoutingKey;

    @Value("${rabbitmq.order-status.queue}")
    private String orderStatusUpdateQueueName;

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(orderExchangeName);
    }

    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(orderQueueName).build();
    }

    @Bean
    public Binding orderBinding(Queue orderQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(orderQueue).to(orderExchange).with(orderRoutingKey);
    }

    @Bean
    public Queue orderStatusUpdateQueue() {
        return QueueBuilder.durable(orderStatusUpdateQueueName).build();
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
