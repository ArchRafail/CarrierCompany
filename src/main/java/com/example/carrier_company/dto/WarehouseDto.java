package com.example.carrier_company.dto;

import lombok.Data;

import java.util.List;


@Data
public class WarehouseDto {
    private Long id;
    private String title;
    private String city;
    private String street;
    private double latitude;
    private double longitude;
    private List<DeliveryDto> deliveriesAsSource;
    private List<DeliveryDto> deliveriesAsDestination;
}
