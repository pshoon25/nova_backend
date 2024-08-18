package com.nova.nova_backend.domain.dto;

import com.nova.nova_backend.domain.entity.Agency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AgencyMissionDTO {
    private String agencyName;
    private String missionNo;
    private String missionType;
    private String missionCategory;
    private String placeMid;
    private String placeName;
    private String placeAddress;
    private Integer dailyWorkload;
    private Integer totalWorkdays;
    private String rankKeyword;
    private String searchKeyword;
    private Integer totalRequest;
    private Date adRequestDate;
    private Date adStartDate;
    private Date adEndDate;
    private String missionStatus;

    public AgencyMissionDTO(
            String agencyName,
            String missionNo,
            String missionType,
            String missionCategory,
            String placeMid,
            String placeName,
            String placeAddress,
            Integer dailyWorkload,
            Integer totalWorkdays,
            String rankKeyword,
            String searchKeyword,
            Integer totalRequest,
            Date adRequestDate,
            Date adStartDate,
            Date adEndDate,
            String missionStatus
    ) {
        this.agencyName = agencyName;
        this.missionNo = missionNo;
        this.missionType = missionType;
        this.missionCategory = missionCategory;
        this.placeMid = placeMid;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.dailyWorkload = dailyWorkload;
        this.totalWorkdays = totalWorkdays;
        this.rankKeyword = rankKeyword;
        this.searchKeyword = searchKeyword;
        this.totalRequest = totalRequest;
        this.adRequestDate = adRequestDate;
        this.adStartDate = adStartDate;
        this.adEndDate = adEndDate;
        this.missionStatus = missionStatus;
    }
}
