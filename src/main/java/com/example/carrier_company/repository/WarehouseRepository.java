package com.example.carrier_company.repository;

import com.example.carrier_company.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    @Query("""
        SELECT w FROM Warehouse w
        WHERE (:id IS NULL OR w.id = :id)
        AND (:title IS NULL OR LOWER(w.title) LIKE CONCAT('%', LOWER(:title), '%'))
        AND (:city IS NULL OR LOWER(w.address.city) LIKE CONCAT('%', LOWER(:city), '%'))
        AND (:street IS NULL OR LOWER(w.address.street) LIKE CONCAT('%', LOWER(:street), '%'))
        AND (:latitude IS NULL OR w.address.location.latitude = :latitude)
        AND (:longitude IS NULL OR w.address.location.longitude = :longitude)
        """)
    Page<Warehouse> findAllBy(Long id, String title, String city, String street, Double latitude,
                                 Double longitude, Pageable pageable);
}
