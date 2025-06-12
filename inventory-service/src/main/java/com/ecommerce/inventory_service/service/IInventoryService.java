package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.dto.request.InventoryRequest;
import com.ecommerce.inventory_service.dto.response.InventoryResponse;

import java.util.UUID;

public interface IInventoryService {
    InventoryResponse updateInventory(InventoryRequest inventoryRequest);
    InventoryResponse getInventoryByProductId(UUID productId);
    void seedInventory(UUID productId, Integer quantity);
}
