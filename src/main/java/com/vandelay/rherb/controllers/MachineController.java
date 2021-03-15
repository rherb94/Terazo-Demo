package com.vandelay.rherb.controllers;

import com.vandelay.rherb.domain.Machine;
import com.vandelay.rherb.domain.PageableResponse;
import com.vandelay.rherb.dto.MachineDto;
import com.vandelay.rherb.service.MachineService;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/machines")
public class MachineController {
    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping
    public PageableResponse getAllMachines(HttpServletRequest request, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int pageSize) {
        return new PageableResponse("machines", machineService.findAll(page, pageSize), request.getRequestURL().toString());
    }

    @GetMapping("/{id}")
    public Machine getMachine(@PathVariable Long id) {
        return machineService.findById(id);
    }

    @GetMapping("/factory")
    public PageableResponse getMachinesByFactoryId(HttpServletRequest request, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int pageSize, @RequestParam Long factoryId) {
        return new PageableResponse("machines/factory", machineService.findAllByFactoryId(page, pageSize, factoryId), request.getRequestURL().toString());
    }

    @PostMapping
    public Machine createMachine(@ApiParam(value = "valid Factory JSON") @RequestBody MachineDto machineDto) {
        return machineService.createMachine(machineDto);
    }

    @DeleteMapping("/{machineId}")
    public void deleteMachine(@PathVariable Long machineId){ machineService.deleteMachine(machineId);}

    @PutMapping("/{machineId}")
    public Machine modifyMachine(@ApiParam(value = "valid Machine JSON") @RequestBody MachineDto machineDto, @PathVariable Long machineId) {
        return machineService.modifyMachine(machineDto, machineId);
    }
}
