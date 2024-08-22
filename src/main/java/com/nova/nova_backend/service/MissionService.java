package com.nova.nova_backend.service;

import com.nova.nova_backend.domain.dto.AgencyMissionDTO;
import com.nova.nova_backend.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final MissionRepository missionRepository;

    public List<AgencyMissionDTO> getAgencyMissionListByAgencyName(String agencyName) {
        return missionRepository.findMissionsByAgencyName(agencyName);
    }
}
