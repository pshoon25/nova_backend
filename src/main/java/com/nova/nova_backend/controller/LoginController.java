package com.nova.nova_backend.controller;

import com.nova.nova_backend.domain.dto.AgencyMissionDTO;
import com.nova.nova_backend.domain.entity.Agency;
import com.nova.nova_backend.service.LoginService;
import com.nova.nova_backend.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    @GetMapping("/login")
    public Map<String, Object> login(@RequestParam("loginId") String loginId,
                                     @RequestParam("password") String password) throws Exception {
        return loginService.checkLoginAuth(loginId, password);
    }
}
