package com.nova.nova_backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "T_AGENCY_POINT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgencyPoint {
    @Id
    @Column(name = "AGENCY_CODE", length = 50)
    private String agencyCode;

    @OneToOne
    @JoinColumn(name = "AGENCY_CODE")
    private Agency agency;

    @Column(name = "AVAILABLE_POINTS")
    private BigDecimal availablePoints;

    @Column(name = "UPDATE_DATE_TIME")
    @Temporal(TemporalType.DATE)
    private Date updateDateTime;
}

