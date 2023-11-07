package com.example.transportcompanybackend.repository;

import com.example.transportcompanybackend.entity.Transporter;
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
        AND (:loadCapacityFrom IS NULL OR :loadCapacityTo IS NULL OR t.loadCapacity BETWEEN :loadCapacityFrom AND :loadCapacityTo)
        AND (:isActive IS NULL OR t.isActive = :isActive)
        """)
    Page<Transporter> findAllBy(Long id, String name, String carModel, Double loadCapacityFrom, Double loadCapacityTo,
                                Boolean isActive, Pageable pageable);

    @Query("""
        SELECT t FROM Transporter t
        WHERE (:searchTerm IS NULL OR LOWER(t.name) LIKE CONCAT('%', LOWER(:searchTerm), '%') OR LOWER(t.carModel) LIKE CONCAT('%', LOWER(:searchTerm), '%'))
        AND (t.isActive = true)
        """)
    Page<Transporter> findAllActive(String searchTerm, Pageable pageable);
}
