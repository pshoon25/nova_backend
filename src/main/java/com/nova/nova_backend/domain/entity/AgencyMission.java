package com.nova.nova_backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "T_AGENCY_MISSION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgencyMission {

    @Id
    @Column(name = "MISSION_NO", length = 50, nullable = false)
    private String missionNo;

    @ManyToOne
    @JoinColumn(name = "AGENCY_CODE", nullable = false)
    private Agency agency;

    @Column(name = "REWARD", length = 50)
    private String reward;

    @Column(name = "MISSION_TYPE", length = 50)
    private String missionType;

    @Column(name = "MISSION_CATEGORY", length = 50)
    private String missionCategory;

    @Column(name = "PLACE_MID", length = 50)
    private String placeMid;

    @Column(name = "PLACE_NAME", length = 50)
    private String placeName;

    @Column(name = "PLACE_ADDRESS", length = 100)
    private String placeAddress;

    @Column(name = "DAILY_WORKLOAD")
    private Integer dailyWorkload;

    @Column(name = "TOTAL_WORKDAYS")
    private Integer totalWorkdays;

    @Column(name = "RANK_KEYWORD", length = 100)
    private String rankKeyword;

    @Column(name = "SEARCH_KEYWORD", length = 100)
    private String searchKeyword;

    @Column(name = "TOTAL_REQUEST")
    private Integer totalRequest;

    @Column(name = "AD_REQUEST_DATE")
    @Temporal(TemporalType.DATE)
    private Date adRequestDate;

    @Column(name = "AD_START_DATE")
    @Temporal(TemporalType.DATE)
    private Date adStartDate;

    @Column(name = "AD_END_DATE")
    @Temporal(TemporalType.DATE)
    private Date adEndDate;

    @Column(name = "MISSION_STATUS", length = 20)
    private String missionStatus;
}
