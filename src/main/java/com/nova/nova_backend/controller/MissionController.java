package com.nova.nova_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mission")
@RequiredArgsConstructor
public class MissionController {

    @GetMapping("/test")
    public String getTest(){
        System.out.println("테스트 연동 성공");
        return "연동 성공";
    }
}