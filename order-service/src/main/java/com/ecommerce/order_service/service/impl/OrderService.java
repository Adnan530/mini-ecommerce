package com.ecommerce.order_service.service.impl;

import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.OrderResponse;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.enums.OrderStatus;
import com.ecommerce.order_service.event.OrderCreatedEvent;
import com.ecommerce.order_service.event.OrderItemEvent;
import com.ecommerce.order_service.mapper.OrderMapper;
import com.ecommerce.order_service.repository.OrderRepository;
import com.ecommerce.order_service.service.IOrderService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.order.exchange}")
    private String orderExchange;

    @Value("${rabbitmq.order.routingKey}")
    private String orderRoutingKey;

    @Override
    @Transactional
    public OrderResponse create(OrderRequest orderRequest) {
        Order order = orderMapper.mapRequestToProduct(orderRequest);
        order.setStatus(OrderStatus.CREATED);
        order.getItems().forEach(item -> item.setOrder(order));
        Order savedOrder = orderRepository.save(order);
        //Publish event
        OrderCreatedEvent event = mapToEvent(savedOrder);
        rabbitTemplate.convertAndSend(orderExchange, orderRoutingKey, event);
        return orderMapper.mapProductToResponse(savedOrder);
    }

    @Override
    public OrderResponse getById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        return orderMapper.mapProductToResponse(order);
    }

    @Override
    public List<OrderResponse> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::mapProductToResponse)
                .toList();
    }

    private OrderCreatedEvent mapToEvent(Order order) {
        List<OrderItemEvent> items = order.getItems().stream()
                .map(item -> new OrderItemEvent(item.getProductId(), item.getQuantity()))
                .collect(Collectors.toList());
        return OrderCreatedEvent.builder()
                .orderId(order.getId())
                .customerId(order.getCustomerId())
                .items(items)
                .build();
    }
}

