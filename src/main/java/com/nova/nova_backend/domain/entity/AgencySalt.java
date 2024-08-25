package com.nova.nova_backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "T_AGENCY_SALT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgencySalt {
    @Id
    @Column(name = "AGENCY_CODE")
    private String agencyCode;

    @OneToOne
    @JoinColumn(name = "AGENCY_CODE")
    private Agency agency;

    @Column(name = "SALT")
    private String salt;
}
