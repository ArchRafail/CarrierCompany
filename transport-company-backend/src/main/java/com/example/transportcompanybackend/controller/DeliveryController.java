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

import java.sql.Timestamp;
import java.time.ZonedDateTime;

import static java.util.Optional.ofNullable;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping()
    public Page<DeliveryDto> getAll(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String warehouseFromTitle,
            @RequestParam(required = false) String warehouseFromAddressCity,
            @RequestParam(required = false) String warehouseToTitle,
            @RequestParam(required = false) String warehouseToAddressCity,
            @RequestParam(required = false) String transporterName,
            @RequestParam(required = false) String cargoName,
            @RequestParam(required = false) Double cargoAmountFrom,
            @RequestParam(required = false) Double cargoAmountTo,
            @RequestParam(required = false) ZonedDateTime createdFrom,
            @RequestParam(required = false) ZonedDateTime createdTo,
            @RequestParam(required = false) ZonedDateTime scheduledFrom,
            @RequestParam(required = false) ZonedDateTime scheduledTo,
            @RequestParam(required = false) ZonedDateTime actualFrom,
            @RequestParam(required = false) ZonedDateTime actualTo,
            @RequestParam(required = false) DeliveryStatus status,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) @ParameterObject Pageable pageable) {
        return deliveryService.getAll(id, warehouseFromTitle, warehouseFromAddressCity, warehouseToTitle,
                warehouseToAddressCity, transporterName, cargoName, cargoAmountFrom, cargoAmountTo,
                ofNullable(createdFrom).map(d -> Timestamp.from(d.toInstant())).orElse(null),
                ofNullable(createdTo).map(d -> Timestamp.from(d.toInstant())).orElse(null),
                ofNullable(scheduledFrom).map(d -> Timestamp.from(d.toInstant())).orElse(null),
                ofNullable(scheduledTo).map(d -> Timestamp.from(d.toInstant())).orElse(null),
                ofNullable(actualFrom).map(d -> Timestamp.from(d.toInstant())).orElse(null),
                ofNullable(actualTo).map(d -> Timestamp.from(d.toInstant())).orElse(null),
                status, pageable);
    }

    @GetMapping("/{id}")
    public DeliveryDto get(@PathVariable Long id) {
        return deliveryService.get(id);
    }

    @PostMapping()
    public DeliveryDto create(@RequestBody DeliveryDto delivery){
        return deliveryService.create(delivery);
    }

    @PatchMapping("/{id}")
    public DeliveryDto patch(@PathVariable Long id, @RequestBody DeliveryDto delivery) {
        return deliveryService.patch(id, delivery);
    }

    @PutMapping("/{id}")
    public DeliveryDto update(@PathVariable Long id, @RequestBody DeliveryDto delivery){
        return deliveryService.update(id, delivery);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        deliveryService.delete(id);
    }

    @PutMapping("/{id}/push")
    public DeliveryDto push(@PathVariable Long id){
        return deliveryService.push(id);
    }

    @PutMapping("/{id}/decline")
    public DeliveryDto decline(@PathVariable Long id){
        return deliveryService.decline(id);
    }

    @GetMapping("/transporter/{transporterId}")
    public Long getQuantityByTransporter(@PathVariable Long transporterId) {
        return deliveryService.getQuantityByTransporter(transporterId);
    }

    @GetMapping("/warehouse/{warehouseId}")
    public Long getQuantityByWarehouse(@PathVariable Long warehouseId) {
        return deliveryService.getQuantityByWarehouse(warehouseId);
    }
}
