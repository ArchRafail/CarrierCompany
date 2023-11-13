package com.example.transportcompanybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveriesOnTimeStructuralDto {
    private OnTimeStatus onTimeStatus;
    private Long quantity;

    public enum OnTimeStatus {
        ON_TIME,
        DELAYED,
        DECLINED,
    }
}
