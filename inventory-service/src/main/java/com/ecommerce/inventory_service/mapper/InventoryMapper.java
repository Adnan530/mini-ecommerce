package com.ecommerce.inventory_service.mapper;

import com.ecommerce.inventory_service.dto.response.InventoryResponse;
import com.ecommerce.inventory_service.entity.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryResponse mapInventoryToResponse(Inventory inventory);
}
