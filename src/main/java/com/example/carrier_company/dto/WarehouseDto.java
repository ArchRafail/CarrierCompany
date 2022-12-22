package com.example.carrier_company.dto;

import lombok.Data;


@Data
public class WarehouseDto {
    private Long id;
    private String title;
    private AddressDto address;
}