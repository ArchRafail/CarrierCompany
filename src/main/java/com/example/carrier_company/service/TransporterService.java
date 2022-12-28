package com.example.carrier_company.service;

import com.example.carrier_company.dto.DeliveryDto;
import com.example.carrier_company.dto.TransporterDto;
import com.example.carrier_company.entity.Transporter;
import com.example.carrier_company.exception.EntityNotFoundException;
import com.example.carrier_company.exception.WrongParametersException;
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

    public TransporterDto create(TransporterDto transporterDto) {
        validateLoadCapacity(transporterDto.getLoadCapacity());
        return mapper.toTransporterDto(transporterRepository.save(mapper.toTransporter(transporterDto)));
    }

    public TransporterDto patch(Long id, TransporterDto transporterDto) {
        Transporter transporter = retrieve(id);
        if (transporterDto.getName() != null)
            transporter.setName(transporterDto.getName());
        if (transporterDto.getCarModel() != null)
            transporter.setCarModel(transporterDto.getCarModel());
        if (transporterDto.getLoadCapacity() != null && validateLoadCapacity(transporterDto.getLoadCapacity()))
            transporter.setLoadCapacity(transporterDto.getLoadCapacity());
        return mapper.toTransporterDto(transporterRepository.save(transporter));
    }

    public TransporterDto update(Long id, TransporterDto transporterDto) {
        Transporter transporter = retrieve(id);
        validateLoadCapacity(transporterDto.getLoadCapacity());
        mapper.mergeTransporter(transporterDto, transporter);
        return mapper.toTransporterDto(transporterRepository.save(transporter));
    }

    public void delete(Long id) {
        transporterRepository.deleteById(id);
    }

    public Transporter retrieve(Long id) {
        return transporterRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Transporter", id));
    }

    public boolean validateLoadCapacity(double loadCapacity) {
        if (loadCapacity < 0)
            throw new WrongParametersException("Load capacity can't be less then zero.");
        return true;
    }
}
