package com.nova.nova_backend.repository;

import com.nova.nova_backend.domain.dto.AgencyItemSummaryDTO;
import com.nova.nova_backend.domain.dto.AgencyPointDTO;
import com.nova.nova_backend.domain.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
    @Query("SELECT MAX(a.agencyCode) FROM Agency a WHERE a.agencyCode LIKE 'NOVA_AGE%'")
    String findMaxAgencyCode();
    @Query("SELECT new com.nova.nova_backend.domain.dto.AgencyItemSummaryDTO(age.agencyCode, age.loginId, age.password, age.agencyName, age.name, age.phoneNum, age.resaleYn, age.userType, age.useYn, age.registerDateTime, "
            + "MAX(CASE WHEN agit.reward = 'NOVA' AND agit.itemName = 'PLACE_SEARCH' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'NOVA' AND agit.itemName = 'PLACE_SEARCH_SAVE' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'NOVA' AND agit.itemName = 'PLACE_SEARCH_SAVE_PREMIUM' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'NOVA' AND agit.itemName = 'PLACE_KEEP' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'NOVA' AND agit.itemName = 'SMARTSTORE_SEARCH' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'OLOCK' AND agit.itemName = 'PLACE_SEARCH' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'OLOCK' AND agit.itemName = 'PLACE_SEARCH_SAVE' THEN agit.itemPrice ELSE null END)) "
            + "FROM Agency age "
            + "LEFT JOIN age.agencyItem agit "
            + "WHERE (:agencyName IS NULL OR age.agencyName LIKE %:agencyName%) "
            + "AND (:resaleYn IS NULL OR age.resaleYn = :resaleYn) "
            + "AND (:useYn IS NULL OR age.useYn = :useYn) "
            + "GROUP BY age.agencyCode, age.loginId, age.password, age.agencyName, age.name, age.phoneNum, age.resaleYn, age.userType, age.useYn, age.registerDateTime")
    List<AgencyItemSummaryDTO> findAgencyItems(@Param("agencyName") String agencyName,
                                               @Param("resaleYn") String resaleYn,
                                               @Param("useYn") String useYn);

    Agency findByAgencyCode(String agencyCode);
    Agency findByLoginId(String loginId);
}
