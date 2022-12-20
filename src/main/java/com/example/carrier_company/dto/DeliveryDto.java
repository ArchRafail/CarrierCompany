package com.example.carrier_company.dto;

import com.example.carrier_company.entity.DeliveryStatus;

import lombok.Data;


@Data
public class DeliveryDto {
    private Long id;
    private WarehouseInfoDto warehouse_from;
    private WarehouseInfoDto warehouse_to;
    private TransporterInfoDto transporter;
    private String cargo_name;
    private double cargo_amount;
    private DeliveryStatus status;
}