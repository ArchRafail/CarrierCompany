package com.example.carrier_company.dto;

import com.example.carrier_company.entity.enums.DeliveryStatus;

import lombok.Data;


@Data
public class DeliveryDto {
    private Long id;
    private WarehouseDto warehouseFrom;
    private WarehouseDto warehouseTo;
    private TransporterDto transporter;
    private String cargoName;
    private double cargoAmount;
    private DeliveryStatus status;
}