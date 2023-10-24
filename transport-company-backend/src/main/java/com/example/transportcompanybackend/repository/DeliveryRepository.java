package com.example.transportcompanybackend.repository;

import com.example.transportcompanybackend.entity.Delivery;
import com.example.transportcompanybackend.entity.enums.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @Query("""
        SELECT d FROM Delivery d
        WHERE (:id IS NULL OR d.id = :id)
        AND (:warehouseFromTitle IS NULL OR LOWER(d.warehouseFrom.title) LIKE CONCAT('%', LOWER(:warehouseFromTitle), '%'))
        AND (:warehouseToTitle IS NULL OR LOWER(d.warehouseTo.title) LIKE CONCAT('%', LOWER(:warehouseToTitle), '%'))
        AND (:transporterName IS NULL OR LOWER(d.transporter.name) LIKE CONCAT('%', LOWER(:transporterName), '%'))
        AND (:cargoName IS NULL OR LOWER(d.cargoName) LIKE CONCAT('%', LOWER(:cargoName), '%'))
        AND (:cargoAmountFrom IS NULL OR :cargoAmountTo IS NULL OR d.cargoAmount BETWEEN :cargoAmountFrom AND :cargoAmountTo)
        AND (:status IS NULL OR d.status = :status)
        """)
    Page<Delivery> findAllBy(Long id, String warehouseFromTitle, String warehouseToTitle, String transporterName,
                             String cargoName, Double cargoAmountFrom, Double cargoAmountTo, DeliveryStatus status,
                             Pageable pageable);

    Page<Delivery> findAllByWarehouseFromId(Long id, Pageable pageable);
    Page<Delivery> findAllByWarehouseToId(Long id, Pageable pageable);
    Page<Delivery> findAllByTransporterId(Long id, Pageable pageable);
}
