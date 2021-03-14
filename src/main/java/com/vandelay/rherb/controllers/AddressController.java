package com.vandelay.rherb.controllers;

import com.vandelay.rherb.domain.Address;
import com.vandelay.rherb.domain.PageableResponse;
import com.vandelay.rherb.dto.AddressDto;
import com.vandelay.rherb.service.AddressService;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/addresses")
@Slf4j
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public PageableResponse getAllAddresses(HttpServletRequest request, int page, int pageSize) {
        return new PageableResponse("addresses", addressService.findAll(page, pageSize), request.getRequestURL().toString());
    }

    @GetMapping("/{id}")
    public Address getAddress(@PathVariable Long id) {
        return addressService.findById(id);
    }

    @PostMapping
    public Address createAddress(@ApiParam(value = "valid Address Json") @RequestBody AddressDto addressDto) {
        log.debug("create, addressDto: " + addressDto);
        return addressService.createAddress(addressDto);
    }

    @DeleteMapping("/{addressId}")
    public void deleteAddress(@PathVariable Long addressId) {
        log.debug("delete, addressId: " + addressId);
        addressService.deleteAddress(addressId);
    }

    @PutMapping("/{addressId}")
    public Address modifyAddress(@ApiParam(value = "valid Address Json") @RequestBody AddressDto addressDto, @PathVariable Long addressId) {
        log.debug("modify, addressDto: " + addressDto);
        return addressService.modifyAddress(addressDto, addressId);
    }
}
