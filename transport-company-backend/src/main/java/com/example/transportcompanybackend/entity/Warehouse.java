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
public class Warehouse {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Boolean isActive;

    @Embedded
    private Address address;

    @ToString.Exclude
    @OneToMany(mappedBy = "warehouseFrom")
    private List<Delivery> deliveriesAsSource;

    @ToString.Exclude
    @OneToMany(mappedBy = "warehouseTo")
    private List<Delivery> deliveriesAsDestination;
}
