package com.example.carrier_company.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;


@Data
@Embeddable
public class Address {
    private String city;
    private String street;

    @Embedded
    private Location location;
}
