package com.example.carrier_company.service;

import com.example.carrier_company.entity.Transporter;
import com.example.carrier_company.exception.EntityNotFoundException;
import com.example.carrier_company.repository.TransporterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class TransporterService {
    private final TransporterRepository transporterRepository;
    public List<Transporter> getAll(){
        return transporterRepository.findAll();
    }

    public Transporter get(Long id) {
        return transporterRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Transporter not found"));
    }

    public void create(Transporter transporter) {
        transporterRepository.save(transporter);
    }

    public void update(Long id, Transporter transporter) {
        Transporter searchedTransporter = get(id);
        searchedTransporter.setName(transporter.getName());
        searchedTransporter.setCar_model(transporter.getCar_model());
        searchedTransporter.setLoad_capacity(transporter.getLoad_capacity());
        transporterRepository.save(searchedTransporter);
    }

    public void delete(Long id) {
        transporterRepository.deleteById(id);
    }
}
