package com.example.carrier_company.repository;

import com.example.carrier_company.entity.Transporter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TransporterRepository extends JpaRepository<Transporter, Long> {
    @Query("""
        SELECT t FROM Transporter t
        WHERE (:id IS NULL OR t.id = :id)
        AND (:name IS NULL OR LOWER(t.name) LIKE CONCAT('%', LOWER(:name), '%'))
        AND (:carModel IS NULL OR LOWER(t.carModel) LIKE CONCAT('%', LOWER(:carModel), '%'))
        AND (:loadCapacity IS NULL OR t.loadCapacity = :loadCapacity)
        """)
    Page<Transporter> findAllBy(Long id, String name, String carModel, Double loadCapacity, Pageable pageable);
}
