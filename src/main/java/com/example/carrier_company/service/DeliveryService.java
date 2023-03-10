package com.example.carrier_company.service;

import com.example.carrier_company.dto.DeliveryDto;
import com.example.carrier_company.dto.TransporterDto;
import com.example.carrier_company.dto.WarehouseDto;
import com.example.carrier_company.entity.*;
import com.example.carrier_company.entity.enums.DeliveryStatus;
import com.example.carrier_company.exception.EntityNotFoundException;
import com.example.carrier_company.exception.WrongParametersException;
import com.example.carrier_company.mapper.Mapper;
import com.example.carrier_company.repository.DeliveryRepository;
import com.example.carrier_company.repository.TransporterRepository;
import com.example.carrier_company.repository.WarehouseRepository;
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
                                    String cargoName, Double cargoAmount, DeliveryStatus status, Pageable pageable){
        return deliveryRepository.findAllBy(id, warehouseFromTitle, warehouseToTitle, transporterName, cargoName,
                        cargoAmount, status, pageable).map(mapper::toDeliveryDto);
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

}
