package com.nova.nova_backend.controller;

import com.nova.nova_backend.domain.dto.AdminMissionDTO;
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
    public List<AdminMissionDTO> getAgencyMissionListByAgencyName(@RequestParam("agencyName") String agencyName,
                                                                  @RequestParam("reward") String reward,
                                                                  @RequestParam("itemName") String itemName){
        return missionService.getAgencyMissionListByAgencyName(agencyName, reward, itemName);
    }

    @GetMapping("/getAgencyMissionListByAgencyCode")
    public List<AgencyMission> getAgencyMissionListByAgencyCode(@RequestParam("agencyCode") String agencyCode,
                                                                @RequestParam("reward") String reward,
                                                                @RequestParam("placeName") String placeName,
                                                                @RequestParam("itemName") String itemName){
        return missionService.getAgencyMissionListByAgencyCode(agencyCode, reward, placeName, itemName);
    }

    @PostMapping("/addMissions")
    public String insertAgencyMission(@RequestBody Map<String, Object> requestMap) {
        return missionService.insertAgencyMission(requestMap);
    }

    @PostMapping("/saveNovaMissionStatus")
    public String saveNovaMissionStatus(@RequestBody List<Map<String, Object>> requestMapList) throws Exception {
        return missionService.saveNovaMissionStatus(requestMapList);
    }
}