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

    public List<Agency> getAgencyList() {
        List<Agency> agencyList = agencyRepository.findAll();
        System.out.println(agencyList);
        return agencyList;
    }

    public Agency insertAgency(Agency agency) {
        agency.setAgencyCode("1234");
        return agencyRepository.save(agency);
    }
}

