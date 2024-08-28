package com.nova.nova_backend.controller;

import com.nova.nova_backend.domain.dto.AgencyPointDTO;
import com.nova.nova_backend.domain.dto.PointHistoryDTO;
import com.nova.nova_backend.domain.entity.Agency;
import com.nova.nova_backend.domain.entity.AgencyPointHistory;
import com.nova.nova_backend.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/getPointHistoryList")
    public List<PointHistoryDTO> getPointHistoryList(@RequestParam(value = "agencyName", required = false) String agencyName,
                                                     @RequestParam(value = "status", required = false) String status) {
        return pointService.getPointHistoryList(agencyName, status);
    }

    @GetMapping("/getAgencyPointByAgencyName")
    public AgencyPointDTO getAgencyPointByAgencyName(@RequestParam("agencyName") String agencyName) {
        return pointService.getAgencyPointByAgencyName(agencyName);
    }

    @GetMapping("/getAgencyPointByAgencyCode")
    public AgencyPointDTO getAgencyPointByAgencyCode(@RequestParam("agencyCode") String agencyCode) {
        return pointService.getAgencyPointByAgencyCode(agencyCode);
    }

    @PostMapping("/requestPointRecharge")
    public String requestPointRecharge(@RequestBody Map<String, Object> requestMap) {
        return pointService.requestPointRecharge(requestMap);
    }

    @PostMapping("/approveRecharge")
    public String approveRecharge(@RequestBody Map<String, Object> requestMap) {
        return pointService.approveRecharge(requestMap);
    }
}
