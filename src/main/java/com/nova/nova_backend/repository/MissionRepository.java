package com.nova.nova_backend.repository;

import com.nova.nova_backend.domain.dto.AdminMissionDTO;
import com.nova.nova_backend.domain.entity.Agency;
import com.nova.nova_backend.domain.entity.AgencyMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissionRepository extends JpaRepository<AgencyMission, Long> {
    @Query("SELECT new com.nova.nova_backend.domain.dto.AdminMissionDTO(" +
            "a.agencyName, " +
            "m.missionNo, " +
            "a.agencyCode, " +
            "m.reward, " +
            "m.itemName, " +
            "m.mid, " +
            "m.placeName, " +
            "m.placeUrl, " +
            "m.priceComparisonId, " +
            "m.mainSearchKeyword, " +
            "m.subSearchKeyword, " +
            "m.rankKeyword, " +
            "m.correctAnswer, " +
            "m.adRequestDate, " +
            "m.adStartDate, " +
            "m.adEndDate, " +
            "m.totalRequest, " +
            "m.dailyWorkload, " +
            "m.totalWorkdays, " +
            "m.missionStatus) " +
            "FROM AgencyMission m " +
            "LEFT JOIN Agency a ON m.agency.agencyCode = a.agencyCode " +
            "WHERE (:agencyName IS NULL OR a.agencyName LIKE %:agencyName%) " +
            "AND m.reward = :reward " +
            "AND (:itemName = '' OR m.itemName = :itemName) " +
            "ORDER BY m.adRequestDate DESC")
    List<AdminMissionDTO> findMissionsByAgencyName(@Param("agencyName") String agencyName,
                                                 @Param("reward") String reward,
                                                 @Param("itemName") String itemName);

    @Query("SELECT a FROM AgencyMission a " +
            "WHERE a.agency.agencyCode = :agencyCode " +
            "AND a.reward = :reward " +
            "AND (:placeName IS NULL OR a.placeName LIKE %:placeName%) " +
            "AND (:itemName = '' OR a.itemName = :itemName) " +
            "ORDER BY a.adRequestDate DESC")
    List<AgencyMission> findMissionsByAgencyCode(@Param("agencyCode") String agencyCode,
                                                 @Param("reward") String reward,
                                                 @Param("placeName") String placeName,
                                                 @Param("itemName") String itemName);

    @Query("SELECT MAX(a.missionNo) FROM AgencyMission a WHERE a.missionNo LIKE :prefix%")
    String findMaxMissionNoStartingWith(@Param("prefix") String prefix);

    @Modifying
    @Query("UPDATE AgencyMission a SET a.missionStatus = :missionStatus WHERE a.missionNo = :missionNo")
    void updateMissionStatus(@Param("missionNo") String missionNo,
                             @Param("missionStatus") String missionStatus);

    AgencyMission findByMissionNo(String missionNo);
}
