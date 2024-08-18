package com.nova.nova_backend.service;

import com.nova.nova_backend.domain.entity.Agency;
import com.nova.nova_backend.repository.AgencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgencyService {
    private final AgencyRepository agencyRepository;

    public List<Agency> getAgencyList(String agencyName) {
        return agencyRepository.findAllByAgencyName(agencyName);
//        return agencyRepository.findAll();
    }

    public Agency insertAgency(Agency agency) {
        agency.setAgencyCode("1234");
        return agencyRepository.save(agency);
    }

    public int updateAgencyInfo(List<Agency> agencyList) {
        int updatedCount = 0;
        for (Agency agency : agencyList) {
            Agency updatedAgency = agencyRepository.save(agency);
            if (updatedAgency != null) {
                updatedCount++;
            }
        }
        return updatedCount;
    }
}

