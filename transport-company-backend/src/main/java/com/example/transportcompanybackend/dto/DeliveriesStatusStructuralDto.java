package com.example.transportcompanybackend.dto;

import com.example.transportcompanybackend.entity.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveriesStatusStructuralDto {
    private DeliveryStatus deliveryStatus;
    private Long quantity;
}
