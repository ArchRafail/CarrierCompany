package com.example.transportcompanybackend.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;


@Data
@Embeddable
public class Location {
    private Double latitude;
    private Double longitude;
}
