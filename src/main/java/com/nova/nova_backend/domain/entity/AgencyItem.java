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
    @Column(name = "ITEM_SEQ", nullable = false)
    private Long itemSeq;

    @ManyToOne
    @JoinColumn(name = "AGENCY_CODE", nullable = false)
    private Agency agency;

    @Column(name = "ITEM_NAME", length = 50, nullable = false)
    private String itemName;

    @Column(name = "ITEM_PRICE", nullable = false)
    private BigDecimal itemPrice;

    @Column(name = "USE_YN", length = 1, nullable = false)
    private String useYn;
}
