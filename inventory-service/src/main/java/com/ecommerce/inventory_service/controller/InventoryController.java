package com.ecommerce.inventory_service.controller;

import com.ecommerce.inventory_service.dto.request.InventoryRequest;
import com.ecommerce.inventory_service.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final IInventoryService inventoryService;

    @PostMapping("/seed")
    public ResponseEntity<String> seedInventory(@RequestBody InventoryRequest inventoryRequest){
        inventoryService.seedInventory(inventoryRequest.getProductId(),
                inventoryRequest.getQuantity());
        return ResponseEntity.ok("Inventory Added");

    }
}
