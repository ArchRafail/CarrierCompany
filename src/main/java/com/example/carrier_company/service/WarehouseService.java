package com.example.carrier_company.service;

import com.example.carrier_company.dto.DeliveryDto;
import com.example.carrier_company.dto.WarehouseDto;
import com.example.carrier_company.entity.Warehouse;
import com.example.carrier_company.exception.EntityNotFoundException;
import com.example.carrier_company.mapper.Mapper;
import com.example.carrier_company.repository.DeliveryRepository;
import com.example.carrier_company.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final DeliveryRepository deliveryRepository;
    private final Mapper mapper;

    public List<WarehouseDto> getAll(){
        return warehouseRepository.findAll().stream().map(mapper::toWarehouseDto).toList();
    }

    public WarehouseDto get(Long id) {
        return mapper.toWarehouseDto(retrieve(id));
    }

    public List<DeliveryDto> getDeliveriesFrom(Long id) {
        return deliveryRepository.findAllByWarehouseFromId(retrieve(id).getId()).stream().map(mapper::toDeliveryDto).toList();
    }

    public List<DeliveryDto> getDeliveriesTo(Long id) {
        return deliveryRepository.findAllByWarehouseToId(retrieve(id).getId()).stream().map(mapper::toDeliveryDto).toList();
    }

    public void create(WarehouseDto warehouseDto) {
        warehouseRepository.save(mapper.toWarehouse(warehouseDto));
    }

    public void update(Long id, WarehouseDto warehouseDto) {
        Warehouse warehouse = retrieve(id);
        mapper.mergeWarehouse(warehouseDto, warehouse);
        warehouseRepository.save(warehouse);
    }

    public void delete(Long id) {
        warehouseRepository.deleteById(id);
    }

    public Warehouse retrieve(Long id) {
        return warehouseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Warehouse", id));
    }

}
