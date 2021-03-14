package com.vandelay.rherb.service;

import com.vandelay.rherb.Exception.ItemNotFoundException;
import com.vandelay.rherb.domain.Inventory;
import com.vandelay.rherb.domain.Warehouse;
import com.vandelay.rherb.dto.InventoryDto;
import com.vandelay.rherb.repository.InventoryRepository;
import com.vandelay.rherb.repository.WarehouseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final WarehouseRepository warehouseRepository;

    public InventoryService(InventoryRepository inventoryRepository, WarehouseRepository warehouseRepository) {
        this.inventoryRepository = inventoryRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public Page<Inventory> findAll(int page, int pageSize) {
        return inventoryRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Inventory findById(Long id) {
        return inventoryRepository.findById(id).orElseThrow(()-> new ItemNotFoundException("Could not find inventory with id: " + id));
    }

    public Page<Inventory> findAllByWarehouseId(int page, int pageSize, Long id) {
        return inventoryRepository.findAllByWarehouseId(PageRequest.of(page, pageSize), id);
    }

    @Transactional
    public Inventory createInventory(InventoryDto inventoryDto) {
        Warehouse warehouse = warehouseRepository.findById(inventoryDto.getWarehouseId()).orElseThrow(()-> new ItemNotFoundException("Could not find Warehouse with id: " + inventoryDto.getWarehouseId()));

        Inventory inventory = Inventory.builder()
                .sku(inventoryDto.getSku())
                .quantity(inventoryDto.getQuantity())
                .name(inventoryDto.getName())
                .description(inventoryDto.getDescription())
                .warehouse(warehouse)
                .build();
        inventoryRepository.save(inventory);
        return inventory;
    }

    @Transactional
    public void deleteInventory(Long inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(()-> new ItemNotFoundException("Could not find inventory with id: " + inventoryId));
        inventoryRepository.delete(inventory);
    }

    @Transactional
    public Inventory modifyInventory(InventoryDto inventoryDto, Long inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(()-> new ItemNotFoundException("Could not find inventory with id: " + inventoryId));
        Warehouse warehouse = warehouseRepository.findById(inventoryDto.getWarehouseId()).orElseThrow(() -> new ItemNotFoundException("Count not find Warehouse with id: " + inventoryDto.getWarehouseId()));

        inventory.setSku(inventoryDto.getSku());
        inventory.setQuantity(inventoryDto.getQuantity());
        inventory.setName(inventoryDto.getName());
        inventory.setDescription(inventoryDto.getDescription());
        inventory.setWarehouse(warehouse);

        inventoryRepository.save(inventory);
        return inventory;
    }
}
