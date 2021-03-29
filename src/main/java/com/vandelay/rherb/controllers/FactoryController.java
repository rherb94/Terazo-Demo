package com.vandelay.rherb.controllers;

import com.vandelay.rherb.domain.Factory;
import com.vandelay.rherb.domain.PageableResponse;
import com.vandelay.rherb.dto.FactoryDto;
import com.vandelay.rherb.service.FactoryService;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/factories")
public class FactoryController {
    private final FactoryService factoryService;

    public FactoryController(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    @GetMapping
    public PageableResponse getAllFactories(HttpServletRequest request, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int pageSize) {
        return new PageableResponse("factories", factoryService.getAllFactories(page, pageSize), request.getRequestURL().toString());
    }

    @GetMapping("/{factoryId}")
    public Factory getFactory(@PathVariable Long factoryId) {
        return factoryService.getFactory(factoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Factory createFactory(@ApiParam(value = "valid Factory JSON") @RequestBody FactoryDto factoryDto) {
        return factoryService.createFactory(factoryDto);
    }

    @DeleteMapping("/{factoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFactory(@PathVariable Long factoryId){factoryService.deleteFactory(factoryId);}

    @PutMapping("/{factoryId}")
    public Factory modifyFactory(@ApiParam (value = "valid Factory JSON") @RequestBody FactoryDto factoryDto, @PathVariable Long factoryId){
        return factoryService.modifyFactory(factoryDto, factoryId);
    }
}
