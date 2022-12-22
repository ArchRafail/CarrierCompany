package com.example.carrier_company.service;

import com.example.carrier_company.dto.AddressDto;
import com.example.carrier_company.dto.WarehouseDto;
import com.example.carrier_company.entity.Address;
import com.example.carrier_company.entity.Warehouse;
import com.example.carrier_company.exception.EntityNotFoundException;
import com.example.carrier_company.mapper.Mapper;
import com.example.carrier_company.repository.AddressRepository;
import com.example.carrier_company.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final WarehouseRepository warehouseRepository;
    private final Mapper mapper;

    public List<AddressDto> getAll(){
        return addressRepository.findAll().stream().map(mapper::toAddressDto).toList();
    }

    public AddressDto get(Long id) {
        return mapper.toAddressDto(retrieve(id));
    }

    public List<WarehouseDto> getWarehouses(Long id) {
        return warehouseRepository.getAllByAddressId(retrieve(id).getId()).stream().map(mapper::toWarehouseDto).toList();
    }

    public void create(AddressDto addressDto) {
        addressRepository.save(mapper.toAddress(addressDto));
    }

    public void update(Long id, AddressDto addressDto) {
        Address address = retrieve(id);
        mapper.mergeAddress(addressDto, address);
        addressRepository.save(address);
    }

    public void delete(Long id) {
        addressRepository.deleteById(id);
    }

    public Address retrieve(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Address", id));
    }
}
