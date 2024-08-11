package com.orummmedia.orummmedia_backend.service;

import com.orummmedia.orummmedia_backend.domain.entity.Agency;
import com.orummmedia.orummmedia_backend.repository.AgencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgencyService {
    private final AgencyRepository agencyRepository;

    public List<Agency> getAgencyList() {
        return agencyRepository.findAll();
    }

    public Agency insertAgency(Agency agency) {
        agency.setAgencyCode("1234");
        return agencyRepository.save(agency);
    }
}
