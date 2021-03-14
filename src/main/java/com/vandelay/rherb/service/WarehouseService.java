package com.vandelay.rherb.service;

import com.vandelay.rherb.Exception.ItemNotFoundException;
import com.vandelay.rherb.domain.Address;
import com.vandelay.rherb.domain.Warehouse;
import com.vandelay.rherb.dto.WarehouseDto;
import com.vandelay.rherb.repository.AddressRepository;
import com.vandelay.rherb.repository.WarehouseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final AddressRepository addressRepository;

    public WarehouseService(WarehouseRepository warehouseRepository, AddressRepository addressRepository) {
        this.warehouseRepository = warehouseRepository;
        this.addressRepository = addressRepository;
    }

    public Page<Warehouse> getAllWarehouses(int page, int pageSize) {
        return warehouseRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Warehouse getWarehouse(Long id) {
        return warehouseRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Count not find Warehouse with id: " + id));
    }

    @Transactional
    public Warehouse createWarehouse(WarehouseDto warehouseDto) {
        Address address = addressRepository.findById(warehouseDto.getAddressId()).orElseThrow(() -> new ItemNotFoundException("Count not create Warehouse, no Address with id: " + warehouseDto.getAddressId()));
        Warehouse warehouse = Warehouse.builder()
                .name(warehouseDto.getName())
                .description(warehouseDto.getDescription())
                .address(address)
                .build();
        warehouseRepository.save(warehouse);
        return warehouse;
    }

    @Transactional
    public void deleteWarehouse(Long warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElseThrow(() -> new ItemNotFoundException("Count not find Warehouse with id: " + warehouseId));
        warehouseRepository.delete(warehouse);
    }

    @Transactional
    public Warehouse modifyWarehouse(WarehouseDto warehouseDto, Long warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElseThrow(() -> new ItemNotFoundException("Count not find Warehouse with id: " + warehouseId));
        Address address = addressRepository.findById(warehouseDto.getAddressId()).orElseThrow(() -> new ItemNotFoundException("Count not modify Warehouse, no Address with id: " + warehouseDto.getAddressId()));

        warehouse.setName(warehouseDto.getName());
        warehouse.setDescription(warehouseDto.getDescription());
        warehouse.setAddress(address);

        warehouseRepository.save(warehouse);
        return warehouse;
    }
}
