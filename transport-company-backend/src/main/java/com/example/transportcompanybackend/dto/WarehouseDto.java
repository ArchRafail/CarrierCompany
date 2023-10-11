package com.example.transportcompanybackend.dto;

import com.example.transportcompanybackend.entity.Address;
import lombok.Data;


@Data
public class WarehouseDto {
    private Long id;
    private String title;
    private Address address;
}