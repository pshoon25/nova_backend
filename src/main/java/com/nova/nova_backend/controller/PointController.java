package com.nova.nova_backend.controller;

import com.nova.nova_backend.domain.dto.AgencyPointDTO;
import com.nova.nova_backend.domain.dto.PointHistoryDTO;
import com.nova.nova_backend.domain.entity.Agency;
import com.nova.nova_backend.domain.entity.AgencyPointHistory;
import com.nova.nova_backend.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/getPointHistoryList")
    public List<PointHistoryDTO> getPointHistoryList(@RequestParam("agencyName") String agencyName) {
        return pointService.getPointHistoryList(agencyName);
    }

    @GetMapping("/getAgencyPointByAgencyName")
    public List<AgencyPointDTO> getAgencyPointByAgencyName(@RequestParam("agencyName") String agencyName) {
        return pointService.getAgencyPointByAgencyName(agencyName);
    }
}
