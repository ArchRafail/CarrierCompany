package com.example.carrier_company.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;


@Data
@Embeddable
public class Location {
    private Double latitude;
    private Double longitude;
}
