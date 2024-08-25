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
    private BigDecimal novaPlaceSearch;
    private BigDecimal novaPlaceSearchSave;
    private BigDecimal novaPlaceSearchSavePremium;
    private BigDecimal novaPlaceKeep;
    private BigDecimal novaSmartstoreSearch;
    private BigDecimal olockPlaceSearch;
    private BigDecimal olockPlaceSearchSave;

    public AgencyPointDTO(BigDecimal availablePoints,
                          BigDecimal novaPlaceSearch, BigDecimal novaPlaceSearchSave, BigDecimal novaPlaceSearchSavePremium, BigDecimal novaPlaceKeep, BigDecimal novaSmartstoreSearch,
                          BigDecimal olockPlaceSearch, BigDecimal olockPlaceSearchSave) {
        this.availablePoints = availablePoints;
        this.novaPlaceSearch = novaPlaceSearch;
        this.novaPlaceSearchSave = novaPlaceSearchSave;
        this.novaPlaceSearchSavePremium = novaPlaceSearchSavePremium;
        this.novaPlaceKeep = novaPlaceKeep;
        this.novaSmartstoreSearch = novaSmartstoreSearch;
        this.olockPlaceSearch = olockPlaceSearch;
        this.olockPlaceSearchSave = olockPlaceSearchSave;
    }
}
