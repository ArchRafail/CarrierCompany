package com.example.transportcompanybackend.service;

import com.example.transportcompanybackend.dto.DeliveriesOnTimeStructuralDto;
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

    public List<DeliveriesOnTimeStructuralDto> deliveriesOnTimeStructuralStatistic() {
        List<DeliveriesOnTimeStructuralDto> deliveriesOnTimeStructuralList = new ArrayList<>() ;
        String[] onTimeStatuses = Arrays.stream(DeliveriesOnTimeStructuralDto.OnTimeStatus.values()).map(Enum::toString).toArray(String[]::new);
        for (String onTimeStatus: onTimeStatuses) {
            DeliveriesOnTimeStructuralDto.OnTimeStatus enumOnTimeStatus = DeliveriesOnTimeStructuralDto.OnTimeStatus.valueOf(onTimeStatus);
            Long quantityOfStatuses = switch (onTimeStatus) {
                case "ON_TIME" -> deliveryRepository.countAllOnTime();
                case "DELAYED" -> deliveryRepository.countAllDelayed();
                case "DECLINED" -> deliveryRepository.countAllByStatus(DeliveryStatus.DECLINED);
                default -> 0L;
            };
            deliveriesOnTimeStructuralList.add(new DeliveriesOnTimeStructuralDto(enumOnTimeStatus, quantityOfStatuses));
        }
        return deliveriesOnTimeStructuralList;
    }
}
