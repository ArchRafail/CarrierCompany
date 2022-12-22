package com.example.carrier_company.controller;

import com.example.carrier_company.dto.AddressDto;
import com.example.carrier_company.dto.WarehouseDto;
import com.example.carrier_company.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService addressService;

    @GetMapping()
    public List<AddressDto> getAll() {
        return addressService.getAll();
    }

    @GetMapping("/{id}")
    public AddressDto get(@PathVariable Long id) {
        return addressService.get(id);
    }

    @GetMapping("/{id}/warehouses")
    public List<WarehouseDto> getWarehouses(@PathVariable Long id) {
        return addressService.getWarehouses(id);
    }

    @PostMapping()
    private void create(@RequestBody AddressDto address){
        addressService.create(address);
    }

    @PutMapping("/{id}")
    private void update(@PathVariable Long id, @RequestBody AddressDto address){
        addressService.update(id, address);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){
        addressService.delete(id);
    }
}
