package com.example.carrier_company.controller;

import com.example.carrier_company.entity.Delivery;
import com.example.carrier_company.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping()
    public List<Delivery> getAll() {
        return deliveryService.getAll();
    }

    @GetMapping("/{id}")
    public Delivery get(@PathVariable Long id) {
        return deliveryService.get(id);
    }

    @PostMapping()
    private void create(@RequestBody Delivery delivery){
        deliveryService.create(delivery);
    }

    @PutMapping("/{id}")
    private void update(@PathVariable Long id, @RequestBody Delivery delivery){
        deliveryService.update(id, delivery);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){
        deliveryService.delete(id);
    }

}
