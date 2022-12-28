package com.example.carrier_company.service;

import com.example.carrier_company.dto.DeliveryDto;
import com.example.carrier_company.dto.WarehouseDto;
import com.example.carrier_company.entity.Address;
import com.example.carrier_company.entity.Location;
import com.example.carrier_company.entity.Warehouse;
import com.example.carrier_company.exception.EntityNotFoundException;
import com.example.carrier_company.mapper.Mapper;
import com.example.carrier_company.repository.DeliveryRepository;
import com.example.carrier_company.repository.WarehouseRepository;
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

    public Page<WarehouseDto> getAll(Long id, String title, String city, String street, Double latitude,
                                     Double longitude, Pageable pageable){
        return warehouseRepository.findAllBy(id, title, city, street, latitude, longitude, pageable).map(mapper::toWarehouseDto);
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

    public void create(WarehouseDto warehouseDto) {
        warehouseRepository.save(mapper.toWarehouse(warehouseDto));
    }

    public void patch(Long id, WarehouseDto warehouseDto) {
        Warehouse warehouse = retrieve(id);
        if (warehouseDto.getTitle() != null)
            warehouse.setTitle(warehouseDto.getTitle());
        Address address = warehouse.getAddress();
        if (warehouseDto.getAddress().getCity() != null)
            address.setCity(warehouseDto.getAddress().getCity());
        if (warehouseDto.getAddress().getStreet() != null)
            address.setStreet(warehouseDto.getAddress().getStreet());
        Location location = address.getLocation();
        if (warehouseDto.getAddress().getLocation().getLatitude() != null)
            location.setLatitude(warehouseDto.getAddress().getLocation().getLatitude());
        if (warehouseDto.getAddress().getLocation().getLongitude() != null)
            location.setLongitude(warehouseDto.getAddress().getLocation().getLongitude());
        address.setLocation(location);
        warehouse.setAddress(address);
        warehouseRepository.save(warehouse);
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
