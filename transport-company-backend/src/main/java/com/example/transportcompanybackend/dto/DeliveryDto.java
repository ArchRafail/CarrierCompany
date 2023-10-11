package com.example.transportcompanybackend.dto;

import com.example.transportcompanybackend.entity.enums.DeliveryStatus;

import lombok.Data;


@Data
public class DeliveryDto {
    private Long id;
    private WarehouseDto warehouseFrom;
    private WarehouseDto warehouseTo;
    private TransporterDto transporter;
    private String cargoName;
    private Double cargoAmount;
    private DeliveryStatus status;
}