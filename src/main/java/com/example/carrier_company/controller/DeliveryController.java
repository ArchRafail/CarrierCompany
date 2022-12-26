package com.example.carrier_company.controller;

import com.example.carrier_company.dto.DeliveryDto;
import com.example.carrier_company.entity.enums.DeliveryStatus;
import com.example.carrier_company.service.DeliveryService;
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
    private void create(@RequestBody DeliveryDto delivery){
        deliveryService.create(delivery);
    }

    @PutMapping("/{id}")
    private void update(@PathVariable Long id, @RequestBody DeliveryDto delivery){
        deliveryService.update(id, delivery);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){
        deliveryService.delete(id);
    }

}
