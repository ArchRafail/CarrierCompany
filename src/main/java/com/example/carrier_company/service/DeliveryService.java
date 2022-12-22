package com.example.carrier_company.service;

import com.example.carrier_company.dto.DeliveryDto;
import com.example.carrier_company.entity.Delivery;
import com.example.carrier_company.entity.Transporter;
import com.example.carrier_company.entity.Warehouse;
import com.example.carrier_company.exception.EntityNotFoundException;
import com.example.carrier_company.exception.WrongParametersException;
import com.example.carrier_company.mapper.Mapper;
import com.example.carrier_company.repository.DeliveryRepository;
import com.example.carrier_company.repository.TransporterRepository;
import com.example.carrier_company.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final WarehouseRepository warehouseRepository;
    private final TransporterRepository transporterRepository;
    private final Mapper mapper;

    public List<DeliveryDto> getAll(){
        return deliveryRepository.findAll().stream().map(mapper::toDeliveryDto).toList();
    }

    public DeliveryDto get(Long id) {
        return mapper.toDeliveryDto(retrieve(id));
    }

    public void create(DeliveryDto deliveryDto) {
        Warehouse warehouseFrom = searchWarehouseFrom(deliveryDto);
        Warehouse warehouseTo = searchWarehouseTo(deliveryDto);
        Transporter transporter = searchTransporter(deliveryDto);
        Delivery delivery = mapper.toDelivery(deliveryDto);
        delivery.setWarehouseFrom(warehouseFrom);
        delivery.setWarehouseTo(warehouseTo);
        delivery.setTransporter(transporter);
        deliveryRepository.save(transporterValidation(delivery));
    }

    public void update(Long id, DeliveryDto deliveryDto) {
        Delivery delivery = retrieve(id);
        Warehouse warehouseFrom = searchWarehouseFrom(deliveryDto);
        Warehouse warehouseTo = searchWarehouseTo(deliveryDto);
        Transporter transporter = searchTransporter(deliveryDto);
        mapper.mergeDelivery(deliveryDto, delivery);
        delivery.setWarehouseFrom(warehouseFrom);
        delivery.setWarehouseTo(warehouseTo);
        delivery.setTransporter(transporter);
        deliveryRepository.save(transporterValidation(delivery));
    }

    public void delete(Long id) {
        deliveryRepository.deleteById(id);
    }

    public Delivery retrieve(Long id) {
        return deliveryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Delivery", id));
    }

    public Warehouse searchWarehouseFrom(DeliveryDto deliveryDto) {
        Long idWarehouse = deliveryDto.getWarehouseFrom().getId();
        return warehouseRepository.findById(idWarehouse)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse", idWarehouse));
    }

    public Warehouse searchWarehouseTo(DeliveryDto deliveryDto) {
        Long idWarehouse = deliveryDto.getWarehouseTo().getId();
        return warehouseRepository.findById(idWarehouse)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse", idWarehouse));
    }

    public Transporter searchTransporter(DeliveryDto deliveryDto) {
        Long idTransporter = deliveryDto.getTransporter().getId();
        return transporterRepository.findById(idTransporter)
                .orElseThrow(() -> new EntityNotFoundException("Transporter", idTransporter));
    }

    public Delivery transporterValidation(Delivery delivery) {
        if (delivery.getCargoAmount() <= delivery.getTransporter().getLoadCapacity()) {
            return delivery;
        }
        throw new WrongParametersException("Cargo amount is more then load capacity!");
    }
}
