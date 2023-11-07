package com.example.transportcompanybackend.service;

import com.example.transportcompanybackend.dto.DeliveryDto;
import com.example.transportcompanybackend.dto.TransporterDto;
import com.example.transportcompanybackend.dto.WarehouseDto;
import com.example.transportcompanybackend.entity.*;
import com.example.transportcompanybackend.entity.enums.DeliveryStatus;
import com.example.transportcompanybackend.exception.EntityNotFoundException;
import com.example.transportcompanybackend.exception.WrongParametersException;
import com.example.transportcompanybackend.mapper.Mapper;
import com.example.transportcompanybackend.repository.DeliveryRepository;
import com.example.transportcompanybackend.repository.TransporterRepository;
import com.example.transportcompanybackend.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final WarehouseRepository warehouseRepository;
    private final TransporterRepository transporterRepository;
    private final Mapper mapper;

    public Page<DeliveryDto> getAll(Long id, String warehouseFromTitle, String warehouseToTitle, String transporterName,
                                    String cargoName, Double cargoAmountFrom, Double cargoAmountTo,
                                    DeliveryStatus status, Pageable pageable){
        return deliveryRepository.findAllBy(id, warehouseFromTitle, warehouseToTitle, transporterName, cargoName,
                cargoAmountFrom, cargoAmountTo, status, pageable).map(mapper::toDeliveryDto);
    }

    public DeliveryDto get(Long id) {
        return mapper.toDeliveryDto(retrieve(id));
    }

    public DeliveryDto create(DeliveryDto deliveryDto) {
        Warehouse warehouseFrom = pickWarehouse(deliveryDto.getWarehouseFrom());
        Warehouse warehouseTo = pickWarehouse(deliveryDto.getWarehouseTo());
        Transporter transporter = pickTransporter(deliveryDto.getTransporter());
        Delivery delivery = mapper.toDelivery(deliveryDto);
        delivery.setWarehouseFrom(warehouseFrom);
        delivery.setWarehouseTo(warehouseTo);
        delivery.setTransporter(transporter);
        if (deliveryDto.getStatus() == null)
            delivery.setStatus(DeliveryStatus.CREATED);
        return mapper.toDeliveryDto(deliveryRepository.save(transporterValidation(delivery)));
    }

    public DeliveryDto patch(Long id, DeliveryDto deliveryDto) {
        Delivery delivery = retrieve(id);
        mapper.patchDelivery(deliveryDto, delivery);
        if (deliveryDto.getWarehouseFrom() != null)
            delivery.setWarehouseFrom(searchWarehouse(deliveryDto.getWarehouseFrom().getId()));
        if (deliveryDto.getWarehouseTo() != null)
            delivery.setWarehouseTo(searchWarehouse(deliveryDto.getWarehouseTo().getId()));
        if (deliveryDto.getTransporter() != null)
            delivery.setTransporter(searchTransporter(deliveryDto.getTransporter().getId()));
        if (deliveryDto.getStatus() != null )
            delivery.setStatus(deliveryDto.getStatus());
        return mapper.toDeliveryDto(deliveryRepository.save(transporterValidation(delivery)));
    }

    public DeliveryDto update(Long id, DeliveryDto deliveryDto) {
        Delivery delivery = retrieve(id);
        Warehouse warehouseFrom = pickWarehouse(deliveryDto.getWarehouseFrom());
        Warehouse warehouseTo = pickWarehouse(deliveryDto.getWarehouseTo());
        Transporter transporter = pickTransporter(deliveryDto.getTransporter());
        mapper.mergeDelivery(deliveryDto, delivery);
        delivery.setWarehouseFrom(warehouseFrom);
        delivery.setWarehouseTo(warehouseTo);
        delivery.setTransporter(transporter);
        if (deliveryDto.getStatus() == null)
            delivery.setStatus(DeliveryStatus.CREATED);
        return mapper.toDeliveryDto(deliveryRepository.save(transporterValidation(delivery)));
    }

    public void delete(Long id) {
        deliveryRepository.deleteById(id);
    }

    public Delivery retrieve(Long id) {
        return deliveryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Delivery", id));
    }

    public Warehouse pickWarehouse(WarehouseDto warehouse) {
        return warehouse == null ? null : searchWarehouse(warehouse.getId());
    }

    public Transporter pickTransporter(TransporterDto transporter) {
        return transporter == null ? null : searchTransporter(transporter.getId());
    }

    public Warehouse searchWarehouse(Long warehouseId) {
        return warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse", warehouseId));
    }

    public Transporter searchTransporter(Long transporterId) {
        return transporterRepository.findById(transporterId)
                .orElseThrow(() -> new EntityNotFoundException("Transporter", transporterId));
    }

    public Delivery transporterValidation(Delivery delivery) {
        if (delivery.getCargoAmount() == null || delivery.getCargoAmount() <= delivery.getTransporter().getLoadCapacity()) {
            return delivery;
        }
        throw new WrongParametersException("Cargo amount is more then transporter's load capacity!");
    }

    public DeliveryDto push(Long id) {
        Delivery delivery = retrieve(id);
        if (delivery.getStatus() == DeliveryStatus.DELIVERED || delivery.getStatus() == DeliveryStatus.DECLINED) {
            return mapper.toDeliveryDto(delivery);
        }
        switch (delivery.getStatus()) {
            case CREATED -> delivery.setStatus(DeliveryStatus.PROCESSING);
            case PROCESSING -> delivery.setStatus(DeliveryStatus.SHIPPING);
            case SHIPPING -> delivery.setStatus(DeliveryStatus.DELIVERED);
        }
        return mapper.toDeliveryDto(deliveryRepository.save(delivery));
    }

    public DeliveryDto decline(Long id) {
        Delivery delivery = retrieve(id);
        if (delivery.getStatus() == DeliveryStatus.DELIVERED || delivery.getStatus() == DeliveryStatus.DECLINED) {
            return mapper.toDeliveryDto(delivery);
        }
        delivery.setStatus(DeliveryStatus.DECLINED);
        return mapper.toDeliveryDto(deliveryRepository.save(delivery));
    }

    public Long getQuantityByTransporter(Long transporterId) {
        searchTransporter(transporterId);
        return deliveryRepository.countByTransporterId(transporterId);
    }

    public Long getQuantityByWarehouse(Long warehouseId) {
        searchWarehouse(warehouseId);
        return deliveryRepository.countByWarehouseId(warehouseId);
    }
}
