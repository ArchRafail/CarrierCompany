package com.example.carrier_company.entity;

import com.example.carrier_company.entity.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Delivery {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "warehouse_from")
    private Warehouse warehouseFrom;

    @ManyToOne
    @JoinColumn(name = "warehouse_to")
    private Warehouse warehouseTo;

    @ManyToOne
    @JoinColumn(name = "transporter_id")
    private Transporter transporter;

    private String cargoName;
    private double cargoAmount;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
