package com.example.transportcompanybackend.dto;

import com.example.transportcompanybackend.entity.enums.DeliveryStatus;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class DeliveryDto {
    private Long id;
    private WarehouseDto warehouseFrom;
    private WarehouseDto warehouseTo;
    private TransporterDto transporter;
    private String cargoName;
    private Double cargoAmount;
    private DeliveryStatus status;
    private Timestamp created;
    private Timestamp scheduled;
    private Timestamp actual;
}