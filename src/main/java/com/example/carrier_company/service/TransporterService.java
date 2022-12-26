package com.example.carrier_company.service;

import com.example.carrier_company.dto.DeliveryDto;
import com.example.carrier_company.dto.TransporterDto;
import com.example.carrier_company.entity.Transporter;
import com.example.carrier_company.exception.EntityNotFoundException;
import com.example.carrier_company.mapper.Mapper;
import com.example.carrier_company.repository.DeliveryRepository;
import com.example.carrier_company.repository.TransporterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class TransporterService {
    private final TransporterRepository transporterRepository;
    private final DeliveryRepository deliveryRepository;
    private final Mapper mapper;

    public Page<TransporterDto> getAll(Long id, String name, String carModel, Double loadCapacity, Pageable pageable) {
        return transporterRepository.findAllBy(id, name, carModel, loadCapacity, pageable).map(mapper::toTransporterDto);
    }

    public TransporterDto get(Long id) {
        return mapper.toTransporterDto(retrieve(id));
    }

    public Page<DeliveryDto> getDeliveries(Long id, Pageable pageable) {
        return deliveryRepository.findAllByTransporterId(retrieve(id).getId(), pageable).map(mapper::toDeliveryDto);
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
