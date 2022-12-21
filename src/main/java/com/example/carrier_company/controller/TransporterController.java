package com.example.carrier_company.controller;

import com.example.carrier_company.dto.DeliveryDto;
import com.example.carrier_company.dto.TransporterDto;
import com.example.carrier_company.service.TransporterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transporters")
public class TransporterController {
    private final TransporterService transporterService;

    @GetMapping()
    public List<TransporterDto> getAll() {
        return transporterService.getAll();
    }

    @GetMapping("/{id}")
    public TransporterDto get(@PathVariable Long id) {
        return transporterService.get(id);
    }

    @GetMapping("/{id}/deliveries")
    public List<DeliveryDto> getDeliveries(@PathVariable Long id) {
        return transporterService.getDeliveries(id);
    }

    @PostMapping()
    private void create(@RequestBody TransporterDto transporter){
        transporterService.create(transporter);
    }

    @PutMapping("/{id}")
    private void update(@PathVariable Long id, @RequestBody TransporterDto transporter){
        transporterService.update(id, transporter);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){
        transporterService.delete(id);
    }

}
