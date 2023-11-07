package com.example.transportcompanybackend.repository;

import com.example.transportcompanybackend.entity.Warehouse;
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
        AND (:latitudeFrom IS NULL OR :latitudeTo IS NULL OR w.address.location.latitude BETWEEN :latitudeFrom AND :latitudeTo)
        AND (:longitudeFrom IS NULL OR :longitudeTo IS NULL OR w.address.location.longitude BETWEEN :longitudeFrom AND :longitudeTo)
        AND (:isActive IS NULL OR w.isActive = :isActive)
        """)
    Page<Warehouse> findAllBy(Long id, String title, String city, String street, Double latitudeFrom,
                              Double latitudeTo, Double longitudeFrom, Double longitudeTo, Boolean isActive,
                              Pageable pageable);

    @Query("""
        SELECT w FROM Warehouse w
        WHERE (:searchTerm IS NULL OR LOWER(w.title) LIKE CONCAT('%', LOWER(:searchTerm), '%') OR LOWER(w.address.city) LIKE CONCAT('%', LOWER(:searchTerm), '%') OR LOWER(w.address.street) LIKE CONCAT('%', LOWER(:searchTerm), '%'))
        AND (w.isActive = true)
        """)
    Page<Warehouse> findAllActive(String searchTerm, Pageable pageable);
}
