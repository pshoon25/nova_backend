package com.nova.nova_backend.controller;

import com.nova.nova_backend.domain.dto.AgencyMissionDTO;
import com.nova.nova_backend.domain.entity.AgencyMission;
import com.nova.nova_backend.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mission")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;
    @GetMapping("/getAgencyMissionListByAgencyName")
    public List<AgencyMissionDTO> getAgencyMissionListByAgencyName(@RequestParam("agencyName") String agencyName){
        return missionService.getAgencyMissionListByAgencyName(agencyName);
    }

    @PostMapping("/addMissions")
    public String insertAgencyMission(@RequestBody Map<String, Object> requestMap) {
        return missionService.insertAgencyMission(requestMap);
    }
}