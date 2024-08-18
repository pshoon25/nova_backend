package com.nova.nova_backend.controller;

import com.nova.nova_backend.domain.dto.AgencyMissionDTO;
import com.nova.nova_backend.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mission")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;
    @GetMapping("/getAgencyMissionListByAgencyName")
    public List<AgencyMissionDTO> getAgencyMissionListByAgencyName(@RequestParam("agencyName") String agencyName){
        return missionService.getAgencyMissionListByAgencyName(agencyName);
    }
}