package com.example.transportcompanybackend.mapper;

import com.example.transportcompanybackend.dto.*;
import com.example.transportcompanybackend.entity.Delivery;
import com.example.transportcompanybackend.entity.Transporter;
import com.example.transportcompanybackend.entity.User;
import com.example.transportcompanybackend.entity.Warehouse;
import com.example.transportcompanybackend.pojo.TokensHolder;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@org.mapstruct.Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface Mapper {
    DeliveryDto toDeliveryDto(Delivery delivery);
    Delivery toDelivery(DeliveryDto deliveryDto);
    @Mapping(target = "warehouseFrom", ignore = true)
    @Mapping(target = "warehouseTo", ignore = true)
    @Mapping(target = "transporter", ignore = true)
    @Mapping(target = "status", ignore = true)
    void mergeDelivery(DeliveryDto deliveryDto, @MappingTarget Delivery delivery);
    @Mapping(target = "warehouseFrom", ignore = true)
    @Mapping(target = "warehouseTo", ignore = true)
    @Mapping(target = "transporter", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "cargoName", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cargoAmount", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchDelivery(DeliveryDto deliveryDto, @MappingTarget Delivery delivery);

    TransporterDto toTransporterDto(Transporter transporter);
    Transporter toTransporter(TransporterDto transporterDto);
    @Mapping(target = "deliveries", ignore = true)
    void mergeTransporter(TransporterDto transporterDto, @MappingTarget Transporter transporter);
    @Mapping(target = "deliveries", ignore = true)
    @Mapping(target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "carModel", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "loadCapacity", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchTransporter(TransporterDto transporterDto, @MappingTarget Transporter transporter);

    WarehouseDto toWarehouseDto(Warehouse warehouse);
    Warehouse toWarehouse(WarehouseDto warehouseDto);
    @Mapping(target = "deliveriesAsSource", ignore = true)
    @Mapping(target = "deliveriesAsDestination", ignore = true)
    void mergeWarehouse(WarehouseDto warehouseDto, @MappingTarget Warehouse warehouse);
    @Mapping(target = "deliveriesAsSource", ignore = true)
    @Mapping(target = "deliveriesAsDestination", ignore = true)
    @Mapping(target = "address", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "address.city", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "address.street", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "address.location", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "address.location.latitude", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "address.location.longitude", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchWarehouse(WarehouseDto warehouseDto, @MappingTarget Warehouse warehouse);

    UserDto toUserDto(User entity);
    void mergeUser(UserDto userDto, @MappingTarget User user);

    User toUser(CreateUserDto entity);

    AuthUserDto toAuthUserDto(User entity);

    TokenResponse toTokenResponse(TokensHolder tokensHolder);
}
