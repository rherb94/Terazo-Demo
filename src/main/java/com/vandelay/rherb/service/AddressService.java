package com.vandelay.rherb.service;

import com.vandelay.rherb.Exception.ItemNotFoundException;
import com.vandelay.rherb.domain.Address;
import com.vandelay.rherb.domain.Factory;
import com.vandelay.rherb.domain.Warehouse;
import com.vandelay.rherb.dto.AddressDto;
import com.vandelay.rherb.repository.AddressRepository;
import com.vandelay.rherb.repository.FactoryRepository;
import com.vandelay.rherb.repository.WarehouseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final WarehouseRepository warehouseRepository;
    private final FactoryRepository factoryRepository;

    public AddressService(AddressRepository addressRepository, WarehouseRepository warehouseRepository, FactoryRepository factoryRepository) {
        this.addressRepository = addressRepository;
        this.warehouseRepository = warehouseRepository;
        this.factoryRepository = factoryRepository;
    }

    public Page<Address> findAll(int page, int pageSize) {
        return addressRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(()-> new ItemNotFoundException("Could not find inventory with id: " + id));
    }

    @Transactional
    public Address createAddress(AddressDto addressDto) {
        Address address = Address.builder()
                .buildingName(addressDto.getBuildingName())
                .streetLine1(addressDto.getStreetLine1())
                .streetLine2(addressDto.getStreetLine2())
                .city(addressDto.getCity())
                .stateProvince(addressDto.getStateProvince())
                .zipPostalCode(addressDto.getZipPostalCode())
                .country(addressDto.getCountry())
                .build();
        addressRepository.save(address);
        return address;
    }

    @Transactional
    public void deleteAddress(Long addressId) {
        List<Factory> factories = factoryRepository.findAllByAddressId(addressId);
        if (factories.size() > 0)
            throw new UnsupportedOperationException("Can't delete Address with id: " + addressId + " because it is in use by a Factory!");
        List<Warehouse> warehouses = warehouseRepository.findAllByAddressId(addressId);
        if (warehouses.size() > 0)
            throw new UnsupportedOperationException("Can't delete Address with id: " + addressId + " because it is in use by a Warehouse!");
        addressRepository.deleteById(addressId);
    }

    @Transactional
    public Address modifyAddress(AddressDto addressDto, Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(()-> new ItemNotFoundException("Could not find inventory with id: " + addressId));
        address.setBuildingName(addressDto.getBuildingName());
        address.setStreetLine1(addressDto.getStreetLine1());
        address.setStreetLine2(addressDto.getStreetLine2());
        address.setCity(addressDto.getCity());
        address.setStateProvince(addressDto.getStateProvince());
        address.setZipPostalCode(addressDto.getZipPostalCode());
        address.setCountry(addressDto.getCountry());
        addressRepository.save(address);
        return address;
    }
}



