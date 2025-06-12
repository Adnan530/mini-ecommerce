package com.ecommerce.inventory_service.service.impl;

import com.ecommerce.inventory_service.dto.request.InventoryRequest;
import com.ecommerce.inventory_service.dto.response.InventoryResponse;
import com.ecommerce.inventory_service.entity.Inventory;
import com.ecommerce.inventory_service.mapper.InventoryMapper;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import com.ecommerce.inventory_service.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService implements IInventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public InventoryResponse updateInventory(InventoryRequest inventoryRequest) {
        Inventory inventory=inventoryRepository.findByProductId(inventoryRequest.getProductId())
                .orElseGet(()->Inventory.builder()
                        .productId(inventoryRequest.getProductId())
                        .availableQuantity(0)
                        .reservedQuantity(0)
                        .build());

        //Simple Logic to increase available quantity
        inventory.setAvailableQuantity(inventory.getAvailableQuantity()+inventoryRequest.getQuantity());
        inventory.setLastModifiedDate(LocalDateTime.now());

        Inventory inventorySaved=inventoryRepository.save(inventory);
        return inventoryMapper.mapInventoryToResponse(inventorySaved);

    }

    @Override
    public InventoryResponse getInventoryByProductId(UUID productId) {
        Inventory inventory=inventoryRepository.findByProductId(productId)
                .orElseThrow(()-> new RuntimeException("Inventory not found for product ID :" +productId));
        return inventoryMapper.mapInventoryToResponse(inventory);
    }

    @Override
    public void seedInventory(UUID productId, Integer quantity){
        Inventory inventory=new Inventory();
        inventory.setProductId(productId);
        inventory.setAvailableQuantity(quantity);
        inventory.setReservedQuantity(0);
        inventoryRepository.save(inventory);
    }
}
