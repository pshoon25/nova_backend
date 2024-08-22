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
    @Column(name = "AGENCY_CODE", length = 50, nullable = false)
    private String agencyCode;

    @Column(name = "LOGIN_ID", length = 50, nullable = false)
    private String loginId;

    @Column(name = "PASSWORD", length = 64, nullable = false)
    private String password;

    @Column(name = "AGENCY_NAME", length = 50, nullable = false)
    private String agencyName;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "PHONE_NUM", length = 20)
    private String phoneNum;

    @Column(name = "RESALE_YN", length = 1, nullable = false)
    private String resaleYn;

    @Column(name = "USER_TYPE", length = 10, nullable = false)
    private String userType;

    @Column(name = "USE_YN", length = 1, nullable = false)
    private String useYn;

    @Column(name = "REGISTER_DATE_TIME")
    private LocalDate registerDateTime;
}

