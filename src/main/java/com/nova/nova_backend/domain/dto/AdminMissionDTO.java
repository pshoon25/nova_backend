package com.nova.nova_backend.domain.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nova.nova_backend.domain.entity.Agency;
import com.nova.nova_backend.domain.entity.AgencyMission;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AdminMissionDTO {
    private String agencyName;
    private String missionNo;
    private String agencyCode;
    private String reward;
    private String itemName;
    private String mid;
    private String placeName;
    private String placeUrl;
    private String priceComparisonId;
    private String mainSearchKeyword;
    private String subSearchKeyword;
    private String rankKeyword;
    private String correctAnswer;
    private Date adRequestDate;
    private Date adStartDate;
    private Date adEndDate;
    private Integer totalRequest;
    private Integer dailyWorkload;
    private Integer totalWorkdays;
    private String missionStatus;

    public AdminMissionDTO(String agencyName, String missionNo, String agencyCode, String reward,
                           String itemName, String mid, String placeName, String placeUrl,
                           String priceComparisonId, String mainSearchKeyword, String subSearchKeyword,
                           String rankKeyword, String correctAnswer, Date adRequestDate, Date adStartDate,
                           Date adEndDate, Integer totalRequest, Integer dailyWorkload, Integer totalWorkdays,
                           String missionStatus) {
        this.agencyName = agencyName;
        this.missionNo = missionNo;
        this.agencyCode = agencyCode;
        this.reward = reward;
        this.itemName = itemName;
        this.mid = mid;
        this.placeName = placeName;
        this.placeUrl = placeUrl;
        this.priceComparisonId = priceComparisonId;
        this.mainSearchKeyword = mainSearchKeyword;
        this.subSearchKeyword = subSearchKeyword;
        this.rankKeyword = rankKeyword;
        this.correctAnswer = correctAnswer;
        this.adRequestDate = adRequestDate;
        this.adStartDate = adStartDate;
        this.adEndDate = adEndDate;
        this.totalRequest = totalRequest;
        this.dailyWorkload = dailyWorkload;
        this.totalWorkdays = totalWorkdays;
        this.missionStatus = missionStatus;
    }
}
