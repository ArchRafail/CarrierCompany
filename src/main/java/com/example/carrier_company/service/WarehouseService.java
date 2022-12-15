package com.example.carrier_company.service;

import com.example.carrier_company.entity.Warehouse;
import com.example.carrier_company.exception.EntityNotFoundException;
import com.example.carrier_company.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public List<Warehouse> getAll(){
        return warehouseRepository.findAll();
    }

    public Warehouse get(Long id) {
        return warehouseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));
    }

    public void create(Warehouse warehouse) {
        warehouseRepository.save(warehouse);
    }

    public void update(Long id, Warehouse warehouse) {
        Warehouse searchedWarehouse = get(id);
        searchedWarehouse.setTitle(warehouse.getTitle());
        searchedWarehouse.setCity(warehouse.getCity());
        searchedWarehouse.setStreet(warehouse.getStreet());
        searchedWarehouse.setLatitude(warehouse.getLatitude());
        searchedWarehouse.setLongitude(warehouse.getLongitude());
        warehouseRepository.save(searchedWarehouse);
    }

    public void delete(Long id) {
        warehouseRepository.deleteById(id);
    }
}
