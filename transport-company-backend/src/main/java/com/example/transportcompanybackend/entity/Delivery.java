package com.example.transportcompanybackend.entity;

import com.example.transportcompanybackend.entity.enums.DeliveryStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;


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
    private Double cargoAmount;

    @NotNull
    private Timestamp created;
    @NotNull
    private Timestamp scheduled;
    private Timestamp actual;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
