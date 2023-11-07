package com.example.transportcompanybackend.controller;

import com.example.transportcompanybackend.dto.DeliveriesStatusStructuralDto;
import com.example.transportcompanybackend.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/statistics")
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping("/deliveries-statuses")
    public List<DeliveriesStatusStructuralDto> deliveriesStatusesStructuralStatistic() {
        return statisticService.deliveriesStatusesStructuralStatistic();
    }
}
