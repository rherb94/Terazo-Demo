package com.vandelay.rherb.controllers;

import com.vandelay.rherb.domain.PageableResponse;
import com.vandelay.rherb.domain.Warehouse;
import com.vandelay.rherb.dto.WarehouseDto;
import com.vandelay.rherb.service.WarehouseService;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/warehouses")
public class WarehouseController {
    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public PageableResponse getAllWarehouses(HttpServletRequest request, int page, int pageSize) {
        return new PageableResponse("warehouses", warehouseService.getAllWarehouses(page, pageSize), request.getRequestURL().toString());
    }

    @GetMapping("/{id}")
    public Warehouse getWarehouse(@PathVariable Long id) {
        return warehouseService.getWarehouse(id);
    }

    @PostMapping
    public Warehouse createWarehouse(@ApiParam(value = "valid Warehouse Json") @RequestBody WarehouseDto warehouseDto) {
        log.debug("warehouseDto: " + warehouseDto);
        return warehouseService.createWarehouse(warehouseDto);
    }

    @DeleteMapping("/{warehouseId}")
    public void deleteWarehouse(@PathVariable Long warehouseId) {
        warehouseService.deleteWarehouse(warehouseId);
    }

    @PutMapping("/{warehouseId}")
    public Warehouse modifyWarehouse(@ApiParam(value = "valid Warehouse Json") @RequestBody WarehouseDto warehouseDto, @PathVariable Long warehouseId) {
        log.debug("modify, warehouseDto: " + warehouseDto);
        return warehouseService.modifyWarehouse(warehouseDto, warehouseId);
    }
}
