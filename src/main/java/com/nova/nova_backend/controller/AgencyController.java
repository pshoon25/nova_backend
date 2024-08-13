package com.nova.nova_backend.controller;

import com.nova.nova_backend.domain.entity.Agency;
import com.nova.nova_backend.service.AgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency")
@RequiredArgsConstructor
public class AgencyController {

    private final AgencyService agencyService;

    @GetMapping("/getAgencyList")
    public List<Agency> getAgencyList() {
        return agencyService.getAgencyList();
    }

    @PostMapping("/insertAgencyInfo")
    public Agency insertAgencyInfo(@RequestBody Agency agency){
        return agencyService.insertAgency(agency);
    }
}
