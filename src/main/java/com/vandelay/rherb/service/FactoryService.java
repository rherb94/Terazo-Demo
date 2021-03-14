package com.vandelay.rherb.service;

import com.vandelay.rherb.Exception.ItemNotFoundException;
import com.vandelay.rherb.domain.Address;
import com.vandelay.rherb.domain.Factory;
import com.vandelay.rherb.dto.FactoryDto;
import com.vandelay.rherb.repository.AddressRepository;
import com.vandelay.rherb.repository.FactoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FactoryService {
    private FactoryRepository factoryRepository;
    private final AddressRepository addressRepository;

    public FactoryService(FactoryRepository factoryRepository, AddressRepository addressRepository) {
        this.factoryRepository = factoryRepository;
        this.addressRepository = addressRepository;
    }

    public Page<Factory> getAllFactories(int page, int pageSize) {
        return factoryRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Factory getFactory(Long id){
        return factoryRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Count not find Factory with id: " + id));
    }

    @Transactional
    public Factory createFactory(FactoryDto factoryDto) {
        Address address = addressRepository.findById(factoryDto.getAddressId()).orElseThrow(() -> new ItemNotFoundException("Could not create Factory, no Address with id: " + factoryDto.getAddressId()));
        Factory factory = Factory.builder()
                .name(factoryDto.getName())
                .description(factoryDto.getDescription())
                .address(address)
                .build();
        factoryRepository.save(factory);
        return factory;
    }

    @Transactional
    public void deleteFactory(Long factoryId) {
        Factory factory = factoryRepository.findById(factoryId).orElseThrow(() -> new ItemNotFoundException("Count not find Factory with id: " + factoryId));
        factoryRepository.delete(factory);
    }

    @Transactional
    public Factory modifyFactory(FactoryDto factoryDto, Long factoryId) {
        Factory factory = factoryRepository.findById(factoryId).orElseThrow(() -> new ItemNotFoundException("Count not find Factory with id: " + factoryId));
        Address address = addressRepository.findById(factoryDto.getAddressId()).orElseThrow(() -> new ItemNotFoundException("Count not find Address with id: " + factoryDto.getAddressId()));
        factory.setName(factoryDto.getName());
        factory.setDescription(factoryDto.getDescription());
        factory.setAddress(address);
        factoryRepository.save(factory);
        return factory;
    }
}
