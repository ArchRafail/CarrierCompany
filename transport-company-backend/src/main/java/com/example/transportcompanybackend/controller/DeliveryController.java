package com.example.transportcompanybackend.controller;

import com.example.transportcompanybackend.dto.DeliveryDto;
import com.example.transportcompanybackend.entity.enums.DeliveryStatus;
import com.example.transportcompanybackend.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping()
    public Page<DeliveryDto> getAll(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String warehouseFromTitle,
            @RequestParam(required = false) String warehouseToTitle,
            @RequestParam(required = false) String transporterName,
            @RequestParam(required = false) String cargoName,
            @RequestParam(required = false) Double cargoAmount,
            @RequestParam(required = false) DeliveryStatus status,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) @ParameterObject Pageable pageable) {
        return deliveryService.getAll(id, warehouseFromTitle, warehouseToTitle, transporterName,
                cargoName, cargoAmount, status, pageable);
    }

    @GetMapping("/{id}")
    public DeliveryDto get(@PathVariable Long id) {
        return deliveryService.get(id);
    }

    @PostMapping()
    private DeliveryDto create(@RequestBody DeliveryDto delivery){
        return deliveryService.create(delivery);
    }

    @PatchMapping("/{id}")
    private DeliveryDto patch(@PathVariable Long id, @RequestBody DeliveryDto delivery) {
        return deliveryService.patch(id, delivery);
    }

    @PutMapping("/{id}")
    private DeliveryDto update(@PathVariable Long id, @RequestBody DeliveryDto delivery){
        return deliveryService.update(id, delivery);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){
        deliveryService.delete(id);
    }

}
