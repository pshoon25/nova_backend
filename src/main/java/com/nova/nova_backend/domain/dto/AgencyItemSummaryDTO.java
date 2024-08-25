package com.nova.nova_backend.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AgencyItemSummaryDTO {
    private String agencyCode;
    private String loginId;
    private String password;
    private String agencyName;
    private String name;
    private String phoneNum;
    private String resaleYn;
    private String userType;
    private String useYn;
    private LocalDate registerDateTime;
    private BigDecimal novaPlaceSearch;
    private BigDecimal novaPlaceSearchSave;
    private BigDecimal novaPlaceSearchSavePremium;
    private BigDecimal novaPlaceKeep;
    private BigDecimal novaSmartstoreSearch;

    private BigDecimal olockPlaceSearch;
    private BigDecimal olockPlaceSearchSave;

    public AgencyItemSummaryDTO(String agencyCode, String loginId, String password, String agencyName, String name,
                                String phoneNum, String resaleYn, String userType, String useYn, LocalDate registerDateTime,
                                BigDecimal novaPlaceSearch, BigDecimal novaPlaceSearchSave, BigDecimal novaPlaceSearchSavePremium,
                                BigDecimal novaPlaceKeep, BigDecimal novaSmartstoreSearch,
                                BigDecimal olockPlaceSearch, BigDecimal olockPlaceSearchSave) {
        this.agencyCode = agencyCode;
        this.loginId = loginId;
        this.password = password;
        this.agencyName = agencyName;
        this.name = name;
        this.phoneNum = phoneNum;
        this.resaleYn = resaleYn;
        this.userType = userType;
        this.useYn = useYn;
        this.registerDateTime = registerDateTime;
        this.novaPlaceSearch = novaPlaceSearch;
        this.novaPlaceSearchSave = novaPlaceSearchSave;
        this.novaPlaceSearchSavePremium = novaPlaceSearchSavePremium;
        this.novaPlaceKeep = novaPlaceKeep;
        this.novaSmartstoreSearch = novaSmartstoreSearch;
        this.olockPlaceSearch = olockPlaceSearch;
        this.olockPlaceSearchSave = olockPlaceSearchSave;
    }
}
