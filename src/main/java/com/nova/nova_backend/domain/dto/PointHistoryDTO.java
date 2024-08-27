package com.nova.nova_backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PointHistoryDTO {
    private String pointHistoryNo;
    private String reward;
    private String agencyName;
    private String missionNo;
    private String content;
    private BigDecimal points;
    private Date registerDateTime;
    private String status;

    public PointHistoryDTO(String pointHistoryNo, String reward, String agencyName, String missionNo, String content, BigDecimal points, Date registerDateTime, String status) {
        this.pointHistoryNo = pointHistoryNo;
        this.reward = reward;
        this.agencyName = agencyName;
        this.missionNo = missionNo;
        this.content = content;
        this.points = points;
        this.registerDateTime = registerDateTime;
        this.status = status;
    }
}
