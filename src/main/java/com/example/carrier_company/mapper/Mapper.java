package com.example.carrier_company.mapper;

import com.example.carrier_company.dto.DeliveryDto;
import com.example.carrier_company.dto.TransporterDto;
import com.example.carrier_company.dto.WarehouseDto;
import com.example.carrier_company.entity.Delivery;
import com.example.carrier_company.entity.Transporter;
import com.example.carrier_company.entity.Warehouse;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@org.mapstruct.Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface Mapper {
    DeliveryDto toDeliveryDto(Delivery delivery);
    Delivery toDelivery(DeliveryDto deliveryDto);
    void mergeDelivery(DeliveryDto deliveryDto, @MappingTarget Delivery delivery);

    TransporterDto toTransporterDto(Transporter transporter);
    @Mapping(target = "deliveries", ignore = true)
    Transporter toTransporter(TransporterDto transporterDto);
    @Mapping(target = "deliveries", ignore = true)
    void mergeTransporter(TransporterDto transporterDto, @MappingTarget Transporter transporter);

    WarehouseDto toWarehouseDto(Warehouse warehouse);
    @Mapping(target = "deliveriesAsSource", ignore = true)
    @Mapping(target = "deliveriesAsDestination", ignore = true)
    Warehouse toWarehouse(WarehouseDto warehouseDto);
    @Mapping(target = "deliveriesAsSource", ignore = true)
    @Mapping(target = "deliveriesAsDestination", ignore = true)
    void mergeWarehouse(WarehouseDto warehouseDto, @MappingTarget Warehouse warehouse);
}
