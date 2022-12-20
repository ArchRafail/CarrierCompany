package com.example.carrier_company.dto;

import lombok.Data;

import java.util.List;


@Data
public class TransporterDto {
    private Long id;
    private String name;
    private String car_model;
    private double load_capacity;
    private List<DeliveryDto> deliveries;
}
