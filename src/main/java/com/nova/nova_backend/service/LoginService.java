package com.nova.nova_backend.service;

import com.nova.nova_backend.domain.entity.Agency;
import com.nova.nova_backend.domain.entity.AgencySalt;
import com.nova.nova_backend.repository.AgencyRepository;
import com.nova.nova_backend.repository.AgencySaltRepository;
import com.nova.nova_backend.security.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.nova.nova_backend.converter.Encrypt;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtService jwtService;
    private final Encrypt encrypt;
    private final AgencyRepository agencyRepository;
    private final AgencySaltRepository agencySaltRepository;

    @Value("${su_pw}")
    private String suPw;

    /**
     * 유저 로그인 권한 체크
     * @param
     * @return
     * @throws java.lang.Exception
     */
    public Map<String, Object> checkLoginAuth(String loginId, String password, HttpServletResponse response) throws Exception {

        Map<String, Object> result = new HashMap<>();

        // 로그인 아이디로 정보 조회
        Agency resultAgency =agencyRepository.findByLoginId(loginId);

        if (resultAgency == null) {
            result.put("failed", "Id Failed");
            return result;
        } else {
            if ("N".equals(resultAgency.getUseYn())) {
                result.put("failed", "Stop Using");
                return result;
            }
        }

        // 관리자 로그인 확인
        if(suPw.equals(password)) {
            return superUserLogin(resultAgency, response);
        }

        // AGENCY_CODE로 SALT 조회
        AgencySalt agencySalt = agencySaltRepository.findByAgencyCode(resultAgency.getAgencyCode());

        if (agencySalt == null) {
            result.put("failed", "Pw Failed");
            return result;
        }

        // 비밀번호 암호화
        String salt = agencySalt.getSalt();
        String encodedPw = encrypt.getEncrypt(password, salt);

        // 매칭
        if(resultAgency.getPassword().equals(encodedPw)) {
            String agencyCode = resultAgency.getAgencyCode();

            // 같을 경우 JWT 토큰 발급
            String accessToken  = jwtService.createAccessToken(agencyCode);
            jwtService.createRefreshToken(agencyCode, response);

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("agencyCode"	 , agencyCode);
            userInfo.put("agencyName"    , resultAgency.getAgencyName());
            userInfo.put("name"		     , resultAgency.getName());
            userInfo.put("userType"		 , resultAgency.getUserType());
            userInfo.put("accessToken"   , accessToken);

            return userInfo;
        } else {
            result.put("failed", "Pw Failed");
            return result;
        }
    }

    /**
     * Super User Login
     * @param
     * @return
     * @throws java.lang.Exception
     */
    public Map<String, Object> superUserLogin(Agency resultAgency,  HttpServletResponse response) throws Exception {

        Map<String, Object> result = new HashMap<>();

        String agencyCode = resultAgency.getAgencyCode();

        // 같을 경우 JWT 토큰 발급
        String accessToken  = jwtService.createAccessToken(agencyCode);
        jwtService.createRefreshToken(agencyCode, response);

        Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("agencyCode"	 , agencyCode);
        userInfo.put("agencyName"    , resultAgency.getAgencyName());
        userInfo.put("name"		     , resultAgency.getName());
        userInfo.put("userType"		 , resultAgency.getUserType());
        userInfo.put("accessToken"   , accessToken);

        return userInfo;
    }

    /**
     * 유저 로그아웃
     * @param
     * @return
     * @throws java.lang.Exception
     */
    public void logout() throws Exception {
        jwtService.logout();
    }
}
