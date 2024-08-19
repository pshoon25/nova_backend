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
    public List<Agency> getAgencyList(@RequestParam(value = "agencyName", required = false) String agencyName,
                                      @RequestParam(value = "resaleYn", required = false) String resaleYn,
                                      @RequestParam(value = "useYn", required = false) String useYn) {
        return agencyService.getAgencyList(agencyName, resaleYn, useYn);
    };

    @PostMapping("/insertAgencyInfo")
    public Agency insertAgencyInfo(@RequestBody Agency agency){
        return agencyService.insertAgency(agency);
    }

    @PutMapping("/updateAgencyInfo")
    public int updateAgencyInfo(@RequestBody List<Agency> agencyList) {
        return agencyService.updateAgencyInfo(agencyList);
    }
}
