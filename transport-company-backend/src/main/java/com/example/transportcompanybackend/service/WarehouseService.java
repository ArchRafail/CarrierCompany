package com.example.transportcompanybackend.service;

import com.example.transportcompanybackend.dto.DeliveryDto;
import com.example.transportcompanybackend.dto.WarehouseDto;
import com.example.transportcompanybackend.entity.Warehouse;
import com.example.transportcompanybackend.exception.ItemNotFoundException;
import com.example.transportcompanybackend.mapper.Mapper;
import com.example.transportcompanybackend.repository.DeliveryRepository;
import com.example.transportcompanybackend.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final DeliveryRepository deliveryRepository;
    private final Mapper mapper;

    public Page<WarehouseDto> getAll(Long id, String title, String city, String street, Double latitudeFrom,
                                     Double latitudeTo, Double longitudeFrom, Double longitudeTo, Boolean isActive,
                                     Pageable pageable){
        return warehouseRepository
                .findAllBy(id, title, city, street, latitudeFrom, latitudeTo, longitudeFrom, longitudeTo, isActive,
                        pageable)
                .map(mapper::toWarehouseDto);
    }

    public WarehouseDto get(Long id) {
        return mapper.toWarehouseDto(retrieve(id));
    }

    public Page<DeliveryDto> getDeliveriesFrom(Long id, Pageable pageable) {
        return deliveryRepository.findAllByWarehouseFromId(retrieve(id).getId(), pageable).map(mapper::toDeliveryDto);
    }

    public Page<DeliveryDto> getDeliveriesTo(Long id, Pageable pageable) {
        return deliveryRepository.findAllByWarehouseToId(retrieve(id).getId(), pageable).map(mapper::toDeliveryDto);
    }

    public WarehouseDto create(WarehouseDto warehouseDto) {
        return mapper.toWarehouseDto(warehouseRepository.save(mapper.toWarehouse(warehouseDto)));
    }

    public WarehouseDto patch(Long id, WarehouseDto warehouseDto) {
        Warehouse warehouse = retrieve(id);
        mapper.patchWarehouse(warehouseDto, warehouse);
        return mapper.toWarehouseDto(warehouseRepository.save(warehouse));
    }

    public WarehouseDto update(Long id, WarehouseDto warehouseDto) {
        Warehouse warehouse = retrieve(id);
        mapper.mergeWarehouse(warehouseDto, warehouse);
        return mapper.toWarehouseDto(warehouseRepository.save(warehouse));
    }

    public WarehouseDto updateActive(Long id, Boolean isActive) {
        Warehouse warehouse = retrieve(id);
        warehouse.setIsActive(isActive);
        return mapper.toWarehouseDto(warehouseRepository.save(warehouse));
    }

    public Page<WarehouseDto> getOptions(String searchTerm, Pageable pageable) {
        return warehouseRepository.findAllActive(searchTerm, pageable).map(mapper::toWarehouseDto);
    }

    private Warehouse retrieve(Long id) {
        return warehouseRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(Warehouse.class, id));
    }
}
