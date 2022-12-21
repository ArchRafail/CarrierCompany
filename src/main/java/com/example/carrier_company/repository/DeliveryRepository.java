package com.example.carrier_company.repository;

import com.example.carrier_company.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByWarehouseFromId(Long id);
    List<Delivery> findAllByWarehouseToId(Long id);
    List<Delivery> findAllByTransporterId(Long id);
}
