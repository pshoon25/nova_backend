package com.nova.nova_backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JwtController {
    private final JwtService jwtService;

    @PostMapping("/jwtCheck")
    public Map<String, String> jwtCheck(@RequestBody Map<String, Object> requestMap) throws Exception {
        return jwtService.isValidTokens(requestMap);
    }
}