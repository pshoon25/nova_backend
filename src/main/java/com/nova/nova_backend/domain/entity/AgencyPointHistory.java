package com.nova.nova_backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "T_AGENCY_POINT_HISTORY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgencyPointHistory {

    @Id
    @Column(name = "POINT_HISTORY_NO")
    private String pointHistoryNo;

    @ManyToOne
    @JoinColumn(name = "AGENCY_CODE")
    private Agency agency;

    @ManyToOne
    @JoinColumn(name = "MISSION_NO")
    private AgencyMission mission;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "POINTS")
    private BigDecimal points;

    @Column(name = "REGISTER_DATE_TIME")
    private LocalDateTime registerDateTime;  // 변경된 부분

    @Column(name = "STATUS")
    private String status;

}
