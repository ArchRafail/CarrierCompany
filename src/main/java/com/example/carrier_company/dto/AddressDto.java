package com.example.carrier_company.dto;

import lombok.Data;


@Data
public class AddressDto {
    private Long id;
    private String city;
    private String street;
    private double latitude;
    private double longitude;
}
