package com.ecommerce.order_service.mapper;

import com.ecommerce.order_service.dto.request.OrderItemRequest;
import com.ecommerce.order_service.dto.response.OrderItemResponse;
import com.ecommerce.order_service.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(OrderItemRequest dto);

    OrderItemResponse toDTO(OrderItem item);
}
