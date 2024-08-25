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
    @Column(name = "MISSION_NO")
    private String missionNo;

    @ManyToOne
    @JoinColumn(name = "AGENCY_CODE")
    private Agency agency;

    @Column(name = "REWARD")
    private String reward;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "MID")
    private String mid;

    @Column(name = "PLACE_NAME")
    private String placeName;

    @Column(name = "PLACE_URL")
    private String placeUrl;

    @Column(name = "PRICE_COMPARISON_ID")
    private String priceComparisonId;

    @Column(name = "MAIN_SEARCH_KEYWORD")
    private String mainSearchKeyword;

    @Column(name = "SUB_SEARCH_KEWYWORD")
    private String subSearchKeyword;

    @Column(name = "RANK_KEYWORD")
    private String rankKeyword;

    @Column(name = "CORRECT_ANSWER")
    private String correctAnswer;

    @Column(name = "AD_REQUEST_DATE")
    @Temporal(TemporalType.DATE)
    private Date adRequestDate;

    @Column(name = "AD_START_DATE")
    @Temporal(TemporalType.DATE)
    private Date adStartDate;

    @Column(name = "AD_END_DATE")
    @Temporal(TemporalType.DATE)
    private Date adEndDate;

    @Column(name = "TOTAL_REQUEST")
    private Integer totalRequest;

    @Column(name = "DAILY_WORKLOAD")
    private Integer dailyWorkload;

    @Column(name = "TOTAL_WORKDAYS")
    private Integer totalWorkdays;

    @Column(name = "MISSION_STATUS", length = 20)
    private String missionStatus;
}
