package com.example.carrier_company.service;

import com.example.carrier_company.entity.Delivery;
import com.example.carrier_company.exception.EntityNotFoundException;
import com.example.carrier_company.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public List<Delivery> getAll(){
        return deliveryRepository.findAll();
    }

    public Delivery get(Long id) {
        return deliveryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Delivery not found"));
    }

    public void create(Delivery delivery) {
        deliveryRepository.save(delivery);
    }

    public void update(Long id, Delivery delivery) {
        Delivery searchedDelivery = get(id);
        searchedDelivery.setWarehouse_from(delivery.getWarehouse_from());
        searchedDelivery.setWarehouse_to(delivery.getWarehouse_to());
        searchedDelivery.setTransporter(delivery.getTransporter());
        searchedDelivery.setCargo_name(delivery.getCargo_name());
        searchedDelivery.setCargo_amount(delivery.getCargo_amount());
        searchedDelivery.setStatus(delivery.getStatus());
        deliveryRepository.save(searchedDelivery);
    }

    public void delete(Long id) {
        deliveryRepository.deleteById(id);
    }
}
