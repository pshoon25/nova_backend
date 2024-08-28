package com.nova.nova_backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "T_AGENCY_DEPOSIT_INFO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgencyDepositInfo {
    @Id
    @Column(name = "POINT_HISTORY_NO")
    private String pointHistoryNo;

    @ManyToOne
    @JoinColumn(name = "AGENCY_CODE", referencedColumnName = "AGENCY_CODE")
    private Agency agency;

    @Column(name = "DEPOSITOR")
    private String depositor;
}
