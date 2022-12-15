package com.example.carrier_company.repository;

import com.example.carrier_company.entity.Transporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransporterRepository extends JpaRepository<Transporter, Long> {
}
