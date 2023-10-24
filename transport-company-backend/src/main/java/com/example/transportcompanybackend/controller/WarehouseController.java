package com.example.transportcompanybackend.controller;

import com.example.transportcompanybackend.dto.DeliveryDto;
import com.example.transportcompanybackend.dto.WarehouseDto;
import com.example.transportcompanybackend.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping()
    public Page<WarehouseDto> getAll(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) Double latitudeFrom,
            @RequestParam(required = false) Double latitudeTo,
            @RequestParam(required = false) Double longitudeFrom,
            @RequestParam(required = false) Double longitudeTo,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) @ParameterObject Pageable pageable) {
        return warehouseService.getAll(id, title, city, street, latitudeFrom, latitudeTo, longitudeFrom, longitudeTo, pageable);
    }

    @GetMapping("/{id}")
    public WarehouseDto get(@PathVariable Long id) {
        return warehouseService.get(id);
    }

    @GetMapping("/{id}/deliveriesFrom")
    public Page<DeliveryDto> getDeliveriesFrom(
            @PathVariable Long id,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) @ParameterObject Pageable pageable) {
        return warehouseService.getDeliveriesFrom(id, pageable);
    }

    @GetMapping("/{id}/deliveriesTo")
    public Page<DeliveryDto> getDeliveriesTo(
            @PathVariable Long id,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) @ParameterObject Pageable pageable) {
        return warehouseService.getDeliveriesTo(id, pageable);
    }

    @PostMapping()
    private WarehouseDto create(@RequestBody WarehouseDto warehouse){
        return warehouseService.create(warehouse);
    }

    @PatchMapping("/{id}")
    private WarehouseDto patch(@PathVariable Long id, @RequestBody WarehouseDto warehouse) {
        return warehouseService.patch(id, warehouse);
    }

    @PutMapping("/{id}")
    private WarehouseDto update(@PathVariable Long id, @RequestBody WarehouseDto warehouse){
        return warehouseService.update(id, warehouse);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){
        warehouseService.delete(id);
    }

    @GetMapping("/list")
    public List<WarehouseDto> getListOfWarehouses() {
        return warehouseService.getListOfTransporters();
    }
}