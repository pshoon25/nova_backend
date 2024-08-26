package com.nova.nova_backend.repository;

import com.nova.nova_backend.domain.entity.AgencyPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.nova.nova_backend.domain.dto.AgencyPointDTO;

import java.util.List;

public interface AgencyPointRepository extends JpaRepository<AgencyPoint, Long> {
    @Query("SELECT ap FROM AgencyPoint ap WHERE ap.agency.agencyName = :agencyName")
    AgencyPoint findByAgencyName(@Param("agencyName") String agencyName);

    @Query("SELECT ap FROM AgencyPoint ap WHERE ap.agency.agencyCode = :agencyCode")
    AgencyPoint findByAgencyCode(@Param("agencyCode") String agencyCode);

    @Query("SELECT new com.nova.nova_backend.domain.dto.AgencyPointDTO( "
            + "agpo.availablePoints, "
            + "MAX(CASE WHEN agit.reward = 'NOVA' AND agit.itemName = 'PLACE_SEARCH' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'NOVA' AND agit.itemName = 'PLACE_SEARCH_SAVE' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'NOVA' AND agit.itemName = 'PLACE_SEARCH_SAVE_PREMIUM' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'NOVA' AND agit.itemName = 'PLACE_KEEP' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'NOVA' AND agit.itemName = 'SMARTSTORE_SEARCH' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'OLOCK' AND agit.itemName = 'PLACE_SEARCH' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'OLOCK' AND agit.itemName = 'PLACE_SEARCH_SAVE' THEN agit.itemPrice ELSE null END)) "
            + "FROM Agency age "
            + "LEFT JOIN age.agencyItem agit "
            + "LEFT JOIN age.agencyPoint agpo "
            + "WHERE age.agencyName = :agencyName "
            + "GROUP BY agpo.availablePoints, age.agencyCode")
    AgencyPointDTO getAgencyPointByAgencyName(@Param("agencyName") String agencyName);

    @Query("SELECT new com.nova.nova_backend.domain.dto.AgencyPointDTO( "
            + "agpo.availablePoints, "
            + "MAX(CASE WHEN agit.reward = 'NOVA' AND agit.itemName = 'PLACE_SEARCH' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'NOVA' AND agit.itemName = 'PLACE_SEARCH_SAVE' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'NOVA' AND agit.itemName = 'PLACE_SEARCH_SAVE_PREMIUM' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'NOVA' AND agit.itemName = 'PLACE_KEEP' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'NOVA' AND agit.itemName = 'SMARTSTORE_SEARCH' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'OLOCK' AND agit.itemName = 'PLACE_SEARCH' THEN agit.itemPrice ELSE null END), "
            + "MAX(CASE WHEN agit.reward = 'OLOCK' AND agit.itemName = 'PLACE_SEARCH_SAVE' THEN agit.itemPrice ELSE null END)) "
            + "FROM Agency age "
            + "LEFT JOIN age.agencyItem agit "
            + "LEFT JOIN age.agencyPoint agpo "
            + "WHERE age.agencyCode = :agencyCode "
            + "GROUP BY agpo.availablePoints, age.agencyCode")
    AgencyPointDTO getAgencyPointByAgencyCode(@Param("agencyCode") String agencyCode);
}
