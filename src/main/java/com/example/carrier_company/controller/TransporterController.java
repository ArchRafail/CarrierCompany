package com.example.carrier_company.controller;

import com.example.carrier_company.dto.DeliveryDto;
import com.example.carrier_company.dto.TransporterDto;
import com.example.carrier_company.service.TransporterService;
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
            @RequestParam(required = false) Double loadCapacity,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) @ParameterObject Pageable pageable) {
        return transporterService.getAll(id, name, carModel, loadCapacity, pageable);
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
    private TransporterDto create(@RequestBody TransporterDto transporter){
        return transporterService.create(transporter);
    }

    @PatchMapping("/{id}")
    private TransporterDto patch(@PathVariable Long id, @RequestBody TransporterDto transporter) {
        return transporterService.patch(id, transporter);
    }

    @PutMapping("/{id}")
    private TransporterDto update(@PathVariable Long id, @RequestBody TransporterDto transporter) {
        return transporterService.update(id, transporter);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){
        transporterService.delete(id);
    }

}
