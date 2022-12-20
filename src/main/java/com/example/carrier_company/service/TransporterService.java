package com.example.carrier_company.service;

import com.example.carrier_company.dto.TransporterDto;
import com.example.carrier_company.entity.Transporter;
import com.example.carrier_company.exception.EntityNotFoundException;
import com.example.carrier_company.mapper.Mapper;
import com.example.carrier_company.repository.TransporterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class TransporterService {
    private final TransporterRepository transporterRepository;
    private final Mapper mapper;

    public List<TransporterDto> getAll(){
        return transporterRepository.findAll().stream().map(mapper::toTransporterDto).toList();
    }

    public TransporterDto get(Long id) {
        return mapper.toTransporterDto(retrieve(id));
    }

    public void create(TransporterDto transporterDto) {
        transporterRepository.save(mapper.toTransporter(transporterDto));
    }

    public void update(Long id, TransporterDto transporterDto) {
        Transporter transporter = retrieve(id);
        mapper.mergeTransporter(transporterDto, transporter);
        transporterRepository.save(transporter);
    }

    public void delete(Long id) {
        transporterRepository.deleteById(id);
    }

    public Transporter retrieve(Long id) {
        return transporterRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Transporter", id));
    }
}
