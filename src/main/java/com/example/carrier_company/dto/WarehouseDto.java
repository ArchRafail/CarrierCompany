package com.example.carrier_company.dto;

import com.example.carrier_company.entity.Address;
import lombok.Data;


@Data
public class WarehouseDto {
    private Long id;
    private String title;
    private Address address;
}