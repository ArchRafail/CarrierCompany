package com.example.transportcompanybackend.dto;

import lombok.Data;


@Data
public class TransporterDto {
    private Long id;
    private String name;
    private String carModel;
    private Double loadCapacity;
    private Boolean isActive;
}
