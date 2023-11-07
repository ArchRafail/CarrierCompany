package com.example.transportcompanybackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Transporter {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String carModel;
    private Double loadCapacity;
    private Boolean isActive;

    @ToString.Exclude
    @OneToMany(mappedBy = "transporter")
    private List<Delivery> deliveries;
}
