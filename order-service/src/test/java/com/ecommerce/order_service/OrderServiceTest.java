package com.ecommerce.order_service;

import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.OrderResponse;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.entity.OrderItem;
import com.ecommerce.order_service.enums.OrderStatus;
import com.ecommerce.order_service.event.OrderCreatedEvent;
import com.ecommerce.order_service.mapper.OrderMapper;
import com.ecommerce.order_service.repository.OrderRepository;
import com.ecommerce.order_service.service.impl.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private OrderService orderService;

    private UUID orderId;
    private Order order;
    private OrderRequest orderRequest;
    private OrderResponse orderResponse;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();

        // Dummy data setup
        orderRequest = new OrderRequest(); // Fill with needed data
        order = new Order();
        order.setId(orderId);
        order.setStatus(OrderStatus.CREATED);
        order.setItems(List.of(new OrderItem())); // Must not be null

        orderResponse = new OrderResponse();
        orderResponse.setId(orderId);
    }

    @Test
    void testCreateOrderSuccess() {
        when(orderMapper.mapRequestToProduct(orderRequest)).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.mapProductToResponse(order)).thenReturn(orderResponse);

        OrderResponse response = orderService.create(orderRequest);

        assertNotNull(response);
        assertEquals(orderId, response.getId());
        verify(orderRepository).save(order);
        verify(rabbitTemplate).convertAndSend(anyString(), anyString(), any(OrderCreatedEvent.class));
    }

    @Test
    void testGetOrderByIdSuccess() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.mapProductToResponse(order)).thenReturn(orderResponse);

        OrderResponse response = orderService.getById(orderId);

        assertNotNull(response);
        assertEquals(orderId, response.getId());
    }

    @Test
    void testGetOrderByIdNotFound() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.getById(orderId));
    }

    @Test
    void testGetAllOrders() {
        List<Order> orders = List.of(order);
        List<OrderResponse> responses = List.of(orderResponse);

        when(orderRepository.findAll()).thenReturn(orders);
        when(orderMapper.mapProductToResponse(order)).thenReturn(orderResponse);

        List<OrderResponse> result = orderService.getAll();

        assertEquals(1, result.size());
        assertEquals(orderId, result.get(0).getId());
    }
}
