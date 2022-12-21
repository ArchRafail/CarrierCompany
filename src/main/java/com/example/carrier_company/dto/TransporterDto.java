package com.example.carrier_company.dto;

import lombok.Data;

import java.util.List;


@Data
public class TransporterDto {
    private Long id;
    private String name;
    private String carModel;
    private double loadCapacity;
    private List<DeliveryDto> deliveries;
}
