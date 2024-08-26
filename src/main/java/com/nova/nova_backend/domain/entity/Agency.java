package com.nova.nova_backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "T_AGENCY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agency {
    @Id
    @Column(name = "AGENCY_CODE")
    private String agencyCode;

    @Column(name = "LOGIN_ID")
    private String loginId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "AGENCY_NAME")
    private String agencyName;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE_NUM")
    private String phoneNum;

    @Column(name = "RESALE_YN")
    private String resaleYn;

    @Column(name = "USER_TYPE")
    private String userType;

    @Column(name = "USE_YN")
    private String useYn;

    @Column(name = "REGISTER_DATE_TIME")
    private LocalDate registerDateTime;

    @OneToMany(mappedBy = "agency")
    private List<AgencyItem> agencyItem;

    @OneToMany(mappedBy = "agency")
    @JsonManagedReference
    private List<AgencyMission> agencyMission;

    @OneToOne(mappedBy = "agency")
    private AgencyPoint agencyPoint;
}

