package com.vandelay.rherb.controllers;

import com.vandelay.rherb.domain.Inventory;
import com.vandelay.rherb.domain.PageableResponse;
import com.vandelay.rherb.dto.InventoryDto;
import com.vandelay.rherb.service.InventoryService;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/inventory")
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public PageableResponse getAllInventory(HttpServletRequest request, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int pageSize) {
        return new PageableResponse("inventory", inventoryService.findAll(page, pageSize), request.getRequestURL().toString());
    }

    @GetMapping("/{inventoryId}")
    public Inventory getInventory(@PathVariable Long inventoryId) {
        return inventoryService.findById(inventoryId);
    }

    @GetMapping("/warehouse")
    public PageableResponse getInventoryByWarehouseId(HttpServletRequest request,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int pageSize, @RequestParam Long warehouseId) {
        return new PageableResponse("inventory/warehouse", inventoryService.findAllByWarehouseId(page, pageSize, warehouseId), request.getRequestURL().toString());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Inventory createInventory(@ApiParam(value = "valid Inventory Json") @RequestBody InventoryDto inventoryDto) {
        log.debug("inventoryDto: " + inventoryDto);
        return inventoryService.createInventory(inventoryDto);
    }

    @DeleteMapping("/{inventoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable Long inventoryId) {
        inventoryService.deleteInventory(inventoryId);
    }

    @PutMapping("/{inventoryId}")
    public Inventory modifyInventory(@ApiParam(value = "valid Inventory Json") @RequestBody InventoryDto inventoryDto, @PathVariable Long inventoryId) {
        log.debug("modify, inventoryDto: " + inventoryDto);
        return inventoryService.modifyInventory(inventoryDto, inventoryId);
    }
}
