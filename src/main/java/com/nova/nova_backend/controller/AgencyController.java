package com.nova.nova_backend.controller;

import com.nova.nova_backend.domain.dto.AgencyItemSummaryDTO;
import com.nova.nova_backend.domain.entity.Agency;
import com.nova.nova_backend.service.AgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agency")
@RequiredArgsConstructor
public class AgencyController {

    private final AgencyService agencyService;

    @GetMapping("/getAgencyList")
    public List<AgencyItemSummaryDTO> getAgencyList(@RequestParam(value = "agencyName", required = false) String agencyName,
                                                    @RequestParam(value = "resaleYn", required = false) String resaleYn,
                                                    @RequestParam(value = "useYn", required = false) String useYn) {
        return agencyService.getAgencyList(agencyName, resaleYn, useYn);
    };

    @GetMapping("/checkLoginIdDuplicate")
    public int checkLoginId(@RequestParam("loginId") String loginId) {
        return agencyService.checkLoginIdDuplicate(loginId);
    }

    @PostMapping("/insertAgencyInfo")
    public Agency insertAgencyInfo(@RequestBody Map<String, Object> requestMap){
        return agencyService.insertAgencyInfo(requestMap);
    }

    @PutMapping("/updateAgencyInfo")
    public int updateAgencyInfo(@RequestBody List<AgencyItemSummaryDTO> agencyItemSummaryDTO) {
        return agencyService.updateAgencyInfo(agencyItemSummaryDTO);
    }
}
