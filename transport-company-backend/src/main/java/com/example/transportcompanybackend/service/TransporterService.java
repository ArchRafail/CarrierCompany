package com.example.transportcompanybackend.service;

import com.example.transportcompanybackend.dto.DeliveryDto;
import com.example.transportcompanybackend.dto.TransporterDto;
import com.example.transportcompanybackend.entity.Transporter;
import com.example.transportcompanybackend.exception.ItemNotFoundException;
import com.example.transportcompanybackend.exception.WrongParametersException;
import com.example.transportcompanybackend.mapper.Mapper;
import com.example.transportcompanybackend.repository.DeliveryRepository;
import com.example.transportcompanybackend.repository.TransporterRepository;
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

    public Page<TransporterDto> getAll(Long id, String name, String carModel, Double loadCapacityFrom,
                                       Double loadCapacityTo, Boolean isActive, Pageable pageable) {
        return transporterRepository
                .findAllBy(id, name, carModel, loadCapacityFrom, loadCapacityTo, isActive, pageable)
                .map(mapper::toTransporterDto);
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
        validateLoadCapacity(transporterDto.getLoadCapacity());
        mapper.patchTransporter(transporterDto, transporter);
        return mapper.toTransporterDto(transporterRepository.save(transporter));
    }

    public TransporterDto update(Long id, TransporterDto transporterDto) {
        Transporter transporter = retrieve(id);
        validateLoadCapacity(transporterDto.getLoadCapacity());
        mapper.mergeTransporter(transporterDto, transporter);
        return mapper.toTransporterDto(transporterRepository.save(transporter));
    }

    public TransporterDto updateActive(Long id, Boolean isActive) {
        Transporter transporter = retrieve(id);
        transporter.setIsActive(isActive);
        return mapper.toTransporterDto(transporterRepository.save(transporter));
    }

    public Page<TransporterDto> getOptions(String searchTerm, Pageable pageable) {
        return transporterRepository.findAllActive(searchTerm, pageable).map(mapper::toTransporterDto);
    }

    private Transporter retrieve(Long id) {
        return transporterRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(Transporter.class, id));
    }

    private void validateLoadCapacity(Double loadCapacity) {
        if (loadCapacity != null && loadCapacity < 0)
            throw new WrongParametersException("Load capacity can't be less then zero.");
    }
}
