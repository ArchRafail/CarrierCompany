package com.example.transportcompanybackend.service;

import com.example.transportcompanybackend.dto.DeliveriesStatusStructuralDto;
import com.example.transportcompanybackend.entity.enums.DeliveryStatus;
import com.example.transportcompanybackend.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
@Service
public class StatisticService {
    private final DeliveryRepository deliveryRepository;

    public List<DeliveriesStatusStructuralDto> deliveriesStatusesStructuralStatistic() {
        List<DeliveriesStatusStructuralDto> deliveriesStatusesStructuralList = new ArrayList<>() ;
        String[] deliveryStatuses = Arrays.stream(DeliveryStatus.values()).map(Enum::toString).toArray(String[]::new);
        for (String deliveryStatus: deliveryStatuses) {
            DeliveryStatus enumDeliveryStatus = DeliveryStatus.valueOf(deliveryStatus);
            Long quantityOfStatuses = deliveryRepository.countAllByStatus(enumDeliveryStatus);
            deliveriesStatusesStructuralList.add(new DeliveriesStatusStructuralDto(enumDeliveryStatus, quantityOfStatuses));
        }
        return deliveriesStatusesStructuralList;
    }
}
