package com.nova.nova_backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "T_AGENCY_ITEM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgencyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_SEQ")
    private Long itemSeq;

    @ManyToOne
    @JoinColumn(name = "AGENCY_CODE")
    private Agency agency;

    @Column(name = "REWARD")
    private String reward;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "ITEM_PRICE")
    private BigDecimal itemPrice;

    @Column(name = "USE_YN")
    private String useYn;
}
