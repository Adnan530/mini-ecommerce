package com.ecommerce.order_service.controller;

import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.GeneralResponse;
import com.ecommerce.order_service.dto.response.OrderResponse;
import com.ecommerce.order_service.service.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GeneralResponse<OrderResponse> create(@Valid @RequestBody OrderRequest productRequest) {
        OrderResponse productResponse=orderService.create(productRequest);
        return GeneralResponse.<OrderResponse>builder()
                .message("Order created successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(productResponse)
                .build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse<OrderResponse> getById(@PathVariable UUID id) {
        OrderResponse orderResponse = orderService.getById(id);
        return GeneralResponse.<OrderResponse>builder()
                .message("Order fetched successfully")
                .statusCode(HttpStatus.OK.value())
                .data(orderResponse)
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse<List<OrderResponse>> getAll() {
        List<OrderResponse> responseList = orderService.getAll();
        return GeneralResponse.<List<OrderResponse>>builder()
                .message("All orders fetched successfully")
                .statusCode(HttpStatus.OK.value())
                .data(responseList)
                .build();
    }
}
