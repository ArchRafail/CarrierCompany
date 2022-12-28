package com.example.carrier_company.dto;

import lombok.Data;


@Data
public class TransporterDto {
    private Long id;
    private String name;
    private String carModel;
    private Double loadCapacity;
}
