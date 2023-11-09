package com.example.transportcompanybackend.repository;

import com.example.transportcompanybackend.entity.Delivery;
import com.example.transportcompanybackend.entity.enums.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;


@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @Query("""
        SELECT d FROM Delivery d
        WHERE (:id IS NULL OR d.id = :id)
        AND (:warehouseFromTitle IS NULL OR LOWER(d.warehouseFrom.title) LIKE CONCAT('%', LOWER(:warehouseFromTitle), '%'))
        AND (:warehouseFromAddressCity IS NULL OR LOWER(d.warehouseFrom.address.city) LIKE CONCAT('%', LOWER(:warehouseFromAddressCity), '%'))
        AND (:warehouseToTitle IS NULL OR LOWER(d.warehouseTo.title) LIKE CONCAT('%', LOWER(:warehouseToTitle), '%'))
        AND (:warehouseToAddressCity IS NULL OR LOWER(d.warehouseTo.address.city) LIKE CONCAT('%', LOWER(:warehouseToAddressCity), '%'))
        AND (:transporterName IS NULL OR LOWER(d.transporter.name) LIKE CONCAT('%', LOWER(:transporterName), '%'))
        AND (:cargoName IS NULL OR LOWER(d.cargoName) LIKE CONCAT('%', LOWER(:cargoName), '%'))
        AND (:cargoAmountFrom IS NULL OR :cargoAmountTo IS NULL OR d.cargoAmount BETWEEN :cargoAmountFrom AND :cargoAmountTo)
        AND (CAST(:createdFrom AS DATE) IS NULL OR CAST(:createdTo AS DATE) IS NULL OR d.created BETWEEN CAST(:createdFrom AS DATE) AND CAST(:createdTo AS DATE))
        AND (CAST(:scheduledFrom AS DATE) IS NULL OR CAST(:scheduledTo AS DATE) IS NULL OR d.created BETWEEN CAST(:scheduledFrom AS DATE) AND CAST(:scheduledTo AS DATE))
        AND (CAST(:actualFrom AS DATE) IS NULL OR CAST(:actualTo AS DATE) IS NULL OR d.created BETWEEN CAST(:actualFrom AS DATE) AND CAST(:actualTo AS DATE))
        AND (:status IS NULL OR d.status = :status)
        """)
    Page<Delivery> findAllBy(Long id, String warehouseFromTitle, String warehouseFromAddressCity,
                             String warehouseToTitle, String warehouseToAddressCity, String transporterName,
                             String cargoName, Double cargoAmountFrom, Double cargoAmountTo, Timestamp createdFrom,
                             Timestamp createdTo, Timestamp scheduledFrom, Timestamp scheduledTo, Timestamp actualFrom,
                             Timestamp actualTo, DeliveryStatus status, Pageable pageable);

    Page<Delivery> findAllByWarehouseFromId(Long id, Pageable pageable);
    Page<Delivery> findAllByWarehouseToId(Long id, Pageable pageable);
    Page<Delivery> findAllByTransporterId(Long id, Pageable pageable);

    @Query("""
        SELECT COUNT(*) FROM Delivery d
        WHERE (d.transporter.id = :transporterId)
        AND (d.status NOT IN (com.example.transportcompanybackend.entity.enums.DeliveryStatus.DELIVERED, com.example.transportcompanybackend.entity.enums.DeliveryStatus.DECLINED))
        """)
    Long countByTransporterId(Long transporterId);

    @Query("""
        SELECT COUNT(*) FROM Delivery d
        WHERE (d.warehouseFrom.id = :warehouseId) OR (d.warehouseTo.id = :warehouseId)
        AND (d.status NOT IN (com.example.transportcompanybackend.entity.enums.DeliveryStatus.DELIVERED, com.example.transportcompanybackend.entity.enums.DeliveryStatus.DECLINED))
        """)
    Long countByWarehouseId(Long warehouseId);

    Long countAllByStatus(DeliveryStatus deliveryStatus);
}
