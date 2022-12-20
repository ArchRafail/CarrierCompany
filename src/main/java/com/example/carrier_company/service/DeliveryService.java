package com.example.carrier_company.service;

import com.example.carrier_company.dto.DeliveryDto;
import com.example.carrier_company.entity.Delivery;
import com.example.carrier_company.exception.EntityNotFoundException;
import com.example.carrier_company.mapper.Mapper;
import com.example.carrier_company.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final Mapper mapper;

    public List<DeliveryDto> getAll(){
        return deliveryRepository.findAll().stream().map(mapper::toDeliveryDto).toList();
    }

    public DeliveryDto get(Long id) {
        return mapper.toDeliveryDto(retrieve(id));
    }

    public void create(DeliveryDto deliveryDto) {
        deliveryRepository.save(mapper.toDelivery(deliveryDto));
    }

    public void update(Long id, DeliveryDto deliveryDto) {
        Delivery delivery = retrieve(id);
        mapper.mergeDelivery(deliveryDto, delivery);
        deliveryRepository.save(delivery);
    }

    public void delete(Long id) {
        deliveryRepository.deleteById(id);
    }

    public Delivery retrieve(Long id) {
        return deliveryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Delivery", id));
    }
}
