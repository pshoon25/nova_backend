package com.nova.nova_backend.service;

import com.nova.nova_backend.domain.dto.AgencyPointDTO;
import com.nova.nova_backend.domain.dto.PointHistoryDTO;
import com.nova.nova_backend.domain.entity.AgencyPoint;
import com.nova.nova_backend.repository.AgencyPointHistoryRepository;
import com.nova.nova_backend.repository.AgencyPointRepository;
import com.nova.nova_backend.repository.AgencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final AgencyPointHistoryRepository agencyPointHistoryRepository;
    private final AgencyPointRepository agencyPointRepository;
    private final AgencyRepository agencyRepository;

    public List<PointHistoryDTO> getPointHistoryList(String agencyName, String status) {
        return agencyPointHistoryRepository.findPointHistoryDetails(agencyName, status);
    }

    public AgencyPointDTO getAgencyPointByAgencyName(String agencyName) {
        return agencyPointRepository.getAgencyPointByAgencyName(agencyName);
    }

    public AgencyPointDTO getAgencyPointByAgencyCode(String agencyCode) {
        return agencyPointRepository.getAgencyPointByAgencyCode(agencyCode);
    }
}
