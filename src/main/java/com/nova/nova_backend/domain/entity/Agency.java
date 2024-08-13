package com.nova.nova_backend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "T_AGENCY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agency {
    @Id
    @Column(name = "AGENCY_CODE", length = 10, nullable = false)
    private String agencyCode; // 대행사 코드

    @Column(name = "LOGIN_ID", length = 20)
    private String loginId; // 로그인 아이디

    @Column(name = "PASSWORD", length = 64)
    private String password; // 패스워드

    @Column(name = "AGENCY_NAME", length = 20)
    private String agencyName; // 대행사명

    @Column(name = "NAME", length = 10)
    private String name; // 담당자

    @Column(name = "PHONE_NUM", length = 20)
    private String phoneNum; // 연락처

    @Column(name = "PLACE_TRAFFIC")
    private BigDecimal placeTraffic; // 플레이스 트래픽

    @Column(name = "PLACE_SAVE")
    private BigDecimal placeSave; // 플레이스 저장

    @Column(name = "PLACE_SAVE_PREMIUM")
    private BigDecimal placeSavePremium; // 플레이스 저장 프리미엄

    @Column(name = "RESALE_YN", length = 1)
    private String resaleYn; // 재판매 여부

    @Column(name = "USER_TYPE", length = 10)
    private String userType; // 사용자 여부

    @Column(name = "USE_YN", length = 1)
    private String useYn; // 사용 여부

    @Column(name = "REGISTER_DATE_TIME")
    private LocalDate registerDateTime; // 등록일자
}

