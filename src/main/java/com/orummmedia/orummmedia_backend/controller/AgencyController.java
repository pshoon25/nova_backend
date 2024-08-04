package com.orummmedia.orummmedia_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agency")
@RequiredArgsConstructor
public class AgencyController {

    @PostMapping("/insertAgencyInfo")
    public String insertAgencyInfo(){
        return "정보 입력 성공";
    }
}
