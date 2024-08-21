package com.nova.nova_backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.accessSecretKey}")
    private String accessSecretKey;

    @Value("${jwt.refreshSecretKey}")
    private String refreshSecretKey;

    public String createAccessToken(String userMngCode) {
        byte[] keyBytes    = Decoders.BASE64.decode(accessSecretKey);
        Key key            = Keys.hmacShaKeyFor(keyBytes);
        Date now           = new Date();
        String accessToken = Jwts.builder().setHeaderParam("type", "jwt")
                .claim("userMngCode", userMngCode)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 5)))  // 액세스 토큰의 만료 기간을 5분으로 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return accessToken;
    }

    public String createRefreshToken(String userMngCode, HttpServletResponse response) {
        byte[] keyBytes = Decoders.BASE64.decode(refreshSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        Date now = new Date();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("userMngCode", userMngCode)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24))) // 만료기간은 1일로 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // 쿠키 설정 및 response에 추가
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .maxAge(24 * 60 * 60)  // 쿠키 만료일을 1년으로 설정 (단위: 초)
                .sameSite("None")                    // SameSite 속성 설정 (크로스 사이트 요청에서 쿠키를 포함시키기 위해 None으로 설정)
                .secure(true)                        // HTTPS에서만 전송
                .path("/")                           // 쿠키의 유효 경로 설정 ("/"로 설정하여 전체 도메인에서 유효하게 만듦)
                .httpOnly(true)                      // HttpOnly 속성 설정 (JavaScript에서 접근 불가능하게 만듦)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());  // 쿠키를 응답 헤더에 추가

        return refreshToken;
    }

    public String getAccessToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }

    public String getRefreshToken(String userMngCode){
         HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
         Cookie[] cookies = request.getCookies();
         if (cookies != null) {
             for (Cookie cookie : cookies) {
                 if ("Set-Cookie".equals(cookie.getName())) {
                     return cookie.getValue();
                 }
             }
         }
         return null;
    }

    public Map<String, String> isValidTokens(Map<String, Object> requestMap){   //엑세스 토큰과 리프레쉬 토큰의 유효성을 둘다 검사
        //check both refresh AND access token
        String accessToken  = getAccessToken();
        String refreshToken = getRefreshToken(String.valueOf(requestMap.get("userMngCode")));
        Map<String, String> result = new HashMap<>();
        if (!isValidAccessToken(accessToken)) {
            return isValidRefreshToken(refreshToken);
        } else {
            result.put("result", "OK");
            return result;
        }
    }

    public boolean isValidAccessToken(String accessToken) {
        try {
            if (accessToken != null) {
                byte[] keyBytes     = Decoders.BASE64.decode(accessSecretKey);
                JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(keyBytes)).build();
                Claims claims       = jwtParser.parseClaimsJws(accessToken).getBody();
                Date expiration     = claims.getExpiration();
                if (expiration != null && !expiration.before(new Date())) {
                    return true;
                }
            }
            return false;
        } catch (JwtException e) {
            return false;
        }
    }

    private Map<String, String> isValidRefreshToken(String refreshToken) {
        Map<String, String> result = new HashMap<>();
        try {
            // Refresh Token이 유효하면 새로운 Access Token을 생성
            if (refreshToken != null) {
                byte[] keyBytes      = Decoders.BASE64.decode(refreshSecretKey);
                JwtParser jwtParser  = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(keyBytes)).build();
                Claims claims        = jwtParser.parseClaimsJws(refreshToken).getBody();
                Date expiration      = claims.getExpiration();
                if (expiration != null && !expiration.before(new Date())) {
                    result.put("accessToken", createAccessToken(String.valueOf(claims.get("userMngCode"))));
                    return result;
                }
            } else {
                result.put("result", "Logout");
                return result;
            }
        } catch (JwtException e) {
            result.put("result", "Logout");
            return result;
        }
        return result;
    }

    public void logout(String agencyCode) {
        // Refresh Token 만료
        HttpServletRequest request   = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        Cookie[] cookies 		     = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Set-Cookie".equals(cookie.getName())) {
                    cookie.setMaxAge(0); 		// 쿠키 만료 시간을 0으로 설정하여 삭제
                    cookie.setPath("/"); 		// 쿠키의 유효 경로 설정
                    response.addCookie(cookie); // 응답에 변경된 쿠키 추가
                    break;
                }
            }
        }
    }
}