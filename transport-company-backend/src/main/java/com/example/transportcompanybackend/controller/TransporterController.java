package com.example.transportcompanybackend.controller;

import com.example.transportcompanybackend.dto.DeliveryDto;
import com.example.transportcompanybackend.dto.TransporterDto;
import com.example.transportcompanybackend.service.TransporterService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transporters")
public class TransporterController {
    private final TransporterService transporterService;

    @GetMapping()
    public Page<TransporterDto> getAll(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String carModel,
            @RequestParam(required = false) Double loadCapacityFrom,
            @RequestParam(required = false) Double loadCapacityTo,
            @RequestParam(required = false) Boolean isActive,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) @ParameterObject Pageable pageable) {
        return transporterService.getAll(id, name, carModel, loadCapacityFrom, loadCapacityTo, isActive, pageable);
    }

    @GetMapping("/{id}")
    public TransporterDto get(@PathVariable Long id) {
        return transporterService.get(id);
    }

    @GetMapping("/{id}/deliveries")
    public Page<DeliveryDto> getDeliveries(
            @PathVariable Long id,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) @ParameterObject Pageable pageable) {
        return transporterService.getDeliveries(id, pageable);
    }

    @PostMapping()
    public TransporterDto create(@RequestBody TransporterDto transporter){
        return transporterService.create(transporter);
    }

    @PatchMapping("/{id}")
    public TransporterDto patch(@PathVariable Long id, @RequestBody TransporterDto transporter) {
        return transporterService.patch(id, transporter);
    }

    @PutMapping("/{id}")
    public TransporterDto update(@PathVariable Long id, @RequestBody TransporterDto transporter) {
        return transporterService.update(id, transporter);
    }

    @GetMapping("/options")
    public Page<TransporterDto> getOptions(
            @RequestParam(required = false) String searchTerm,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return transporterService.getOptions(searchTerm, pageable);
    }

    @PatchMapping("/{id}/active")
    public TransporterDto updateActive(@PathVariable Long id, @RequestParam(defaultValue = "true") boolean isActive) {
        return transporterService.updateActive(id, isActive);
    }
}
