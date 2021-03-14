package com.vandelay.rherb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineDto {
    private String name;
    private String description;
    private Long factoryId;
}
