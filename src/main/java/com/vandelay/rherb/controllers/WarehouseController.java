package com.vandelay.rherb.controllers;

import com.vandelay.rherb.domain.PageableResponse;
import com.vandelay.rherb.domain.Warehouse;
import com.vandelay.rherb.dto.WarehouseDto;
import com.vandelay.rherb.service.WarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@Api(value = "Vandelay Industries is a vertically integrated chemical manufaturing, import/export, and sales enterprise headquartered in the Upper West Side, NYC. It was founded by Art Vandely in 1990.\n" +
        "\n" +
        "It is known both for the quality of its product as well as its aggressive salesperson recruiting program.\n" +
        "\n" +
        "The Vandelay Industries API platform, documented here, represents a unified view of its ERP and CRM systems for use by both internal applications and 3rd-party strategic partner system integrations.\n" +
        "\n")
@RequestMapping("/warehouses")
public class WarehouseController {
    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public PageableResponse getAllWarehouses(HttpServletRequest request, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int pageSize) {
        return new PageableResponse("warehouses", warehouseService.getAllWarehouses(page, pageSize), request.getRequestURL().toString());
    }

    @GetMapping("/{warehouseId}")
    public Warehouse getWarehouse(@PathVariable Long warehouseId) {
        return warehouseService.getWarehouse(warehouseId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Warehouse createWarehouse(@ApiParam(value = "valid Warehouse Json") @RequestBody WarehouseDto warehouseDto) {
        log.debug("warehouseDto: " + warehouseDto);
        return warehouseService.createWarehouse(warehouseDto);
    }

    @DeleteMapping("/{warehouseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWarehouse(@PathVariable Long warehouseId) {
        warehouseService.deleteWarehouse(warehouseId);
    }

    @PutMapping("/{warehouseId}")
    public Warehouse modifyWarehouse(@ApiParam(value = "valid Warehouse Json") @RequestBody WarehouseDto warehouseDto, @PathVariable Long warehouseId) {
        log.debug("modify, warehouseDto: " + warehouseDto);
        return warehouseService.modifyWarehouse(warehouseDto, warehouseId);
    }
}
