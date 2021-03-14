package com.vandelay.rherb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private String buildingName;
    private String streetLine1;
    private String streetLine2;
    private String city;
    private String stateProvince;
    private String zipPostalCode;
    private String country;
}
