package com.nova.nova_backend.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AgencyPointDTO {
    private BigDecimal availablePoints;
    private BigDecimal placeTraffic;
    private BigDecimal placeSave;
    private BigDecimal placeSavePremium;

    public AgencyPointDTO(BigDecimal availablePoints, BigDecimal placeTraffic, BigDecimal placeSave, BigDecimal placeSavePremium) {
        this.availablePoints = availablePoints;
        this.placeTraffic = placeTraffic;
        this.placeSave = placeSave;
        this.placeSavePremium = placeSavePremium;
    }
}
