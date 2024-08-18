package com.nova.nova_backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "T_AGENCY_POINT_HISTORY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgencyPointHistory {

    @Id
    @Column(name = "POINT_HISTORY_NO", length = 10)
    private String pointHistoryNo;

    @ManyToOne
    @JoinColumn(name = "AGENCY_CODE", nullable = false)
    private Agency agency;

    @ManyToOne
    @JoinColumn(name = "MISSION_NO", nullable = false)
    private AgencyMission mission;


    @Column(name = "CONTENT", length = 200)
    private String content;

    @Column(name = "POINTS")
    private BigDecimal points;

    @Column(name = "REGISTER_DATE_TIME")
    @Temporal(TemporalType.DATE)
    private Date registerDateTime;

    @Column(name = "STATUS", length = 10)
    private String status;
}
