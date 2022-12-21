package com.example.carrier_company.dto;

import lombok.Data;


@Data
public class TransporterInfoDto {
    private Long id;
    private String name;
    private String carModel;
    private double loadCapacity;
}
