package com.vandelay.rherb.service;

import com.vandelay.rherb.Exception.ItemNotFoundException;
import com.vandelay.rherb.domain.Factory;
import com.vandelay.rherb.domain.Machine;
import com.vandelay.rherb.dto.MachineDto;
import com.vandelay.rherb.repository.FactoryRepository;
import com.vandelay.rherb.repository.MachineRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MachineService {
    private final MachineRepository machineRepository;
    private final FactoryRepository factoryRepository;

    public MachineService(MachineRepository machineRepository, FactoryRepository factoryRepository) {
        this.machineRepository = machineRepository;
        this.factoryRepository = factoryRepository;
    }

    public Page<Machine> findAll(int page, int pageSize) {
        return machineRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Machine findById(Long id) {
        return machineRepository.findById(id).orElseThrow(()-> new ItemNotFoundException("Could not find Machine with id: " + id));
    }

    public Page<Machine> findAllByFactoryId(int page, int pageSize, Long id) {
        Factory factory = factoryRepository.findById(id).orElseThrow(()-> new ItemNotFoundException("Could not find Factory with id: " + id));
        return machineRepository.findAllByFactoryId(PageRequest.of(page, pageSize), factory.getId());
    }

    @Transactional
    public Machine createMachine(MachineDto machineDto) {
        Factory factory = factoryRepository.findById(machineDto.getFactoryId()).orElseThrow(()-> new ItemNotFoundException("Could not find Factory with id: " + machineDto.getFactoryId()));
        Machine machine = Machine.builder()
                .description(machineDto.getDescription())
                .name(machineDto.getName())
                .factory(factory)
                .build();
        machineRepository.save(machine);
        return machine;
    }

    @Transactional
    public void deleteMachine(Long machineId) {
        Machine machine = machineRepository.findById(machineId).orElseThrow(()-> new ItemNotFoundException("Could not find machine with id: " + machineId));
        machineRepository.delete(machine);
    }

    @Transactional
    public Machine modifyMachine(MachineDto machineDto, Long machineId) {
        Machine machine = machineRepository.findById(machineId).orElseThrow(()-> new ItemNotFoundException("Could not find machine with id: " + machineId));
        Factory factory = factoryRepository.findById(machineDto.getFactoryId()).orElseThrow(() -> new ItemNotFoundException("Count not find Factory with id: " + machineDto.getFactoryId()));

        machine.setDescription(machineDto.getDescription());
        machine.setName(machineDto.getName());
        machine.setFactory(factory);

        machineRepository.save(machine);
        return machine;
    }
}
