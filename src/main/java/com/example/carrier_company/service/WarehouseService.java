package com.example.carrier_company.service;

import com.example.carrier_company.dto.WarehouseDto;
import com.example.carrier_company.entity.Warehouse;
import com.example.carrier_company.exception.EntityNotFoundException;
import com.example.carrier_company.mapper.Mapper;
import com.example.carrier_company.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final Mapper mapper;

    public List<WarehouseDto> getAll(){
        return warehouseRepository.findAll().stream().map(mapper::toWarehouseDto).toList();
    }

    public WarehouseDto get(Long id) {
        return warehouseRepository.findById(id).map(mapper::toWarehouseDto)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));
    }

    public void create(WarehouseDto warehouseDto) {
        warehouseRepository.save(mapper.toWarehouse(warehouseDto));
    }

    public void update(Long id, WarehouseDto warehouseDto) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));
        mapper.mergeWarehouse(warehouseDto, warehouse);
        warehouseRepository.save(warehouse);
    }

    public void delete(Long id) {
        warehouseRepository.deleteById(id);
    }
}
