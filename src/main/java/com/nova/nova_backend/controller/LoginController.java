package com.nova.nova_backend.controller;

import com.nova.nova_backend.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 유저 로그아웃
     * @param
     * @return
     * @throws java.lang.Exception
     */
    @PostMapping("/logout")
    public void userLogout(@RequestBody String agencyCode) throws Exception {
        loginService.logout(agencyCode);
    }
}
