package com.example.carrier_company.controller;

import com.example.carrier_company.dto.DeliveryDto;
import com.example.carrier_company.dto.WarehouseDto;
import com.example.carrier_company.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping()
    public List<WarehouseDto> getAll() {
        return warehouseService.getAll();
    }

    @GetMapping("/{id}")
    public WarehouseDto get(@PathVariable Long id) {
        return warehouseService.get(id);
    }

    @GetMapping("/{id}/deliveriesFrom")
    public List<DeliveryDto> getDeliveriesFrom(@PathVariable Long id) {
        return warehouseService.getDeliveriesFrom(id);
    }

    @GetMapping("/{id}/deliveriesTo")
    public List<DeliveryDto> getDeliveriesTo(@PathVariable Long id) {
        return warehouseService.getDeliveriesTo(id);
    }

    @PostMapping()
    private void create(@RequestBody WarehouseDto warehouse){
        warehouseService.create(warehouse);
    }

    @PutMapping("/{id}")
    private void update(@PathVariable Long id, @RequestBody WarehouseDto warehouse){
        warehouseService.update(id, warehouse);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){
        warehouseService.delete(id);
    }
}
