package com.nova.nova_backend.repository;

import com.nova.nova_backend.domain.dto.AgencyMissionDTO;
import com.nova.nova_backend.domain.entity.AgencyMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissionRepository extends JpaRepository<AgencyMission, Long> {
    @Query("SELECT new com.nova.nova_backend.domain.dto.AgencyMissionDTO(" +
            "age.agencyName, mis.missionNo, mis.missionType, mis.missionCategory, mis.placeMid, " +
            "mis.placeName, mis.placeAddress, mis.dailyWorkload, mis.totalWorkdays, mis.rankKeyword, " +
            "mis.searchKeyword, mis.totalRequest, mis.adRequestDate, mis.adStartDate, mis.adEndDate, " +
            "mis.missionStatus) " +
            "FROM AgencyMission mis " +
            "LEFT JOIN Agency age ON mis.agency = age " +
            "WHERE (:agencyName IS NULL OR age.agencyName LIKE %:agencyName%)")
    List<AgencyMissionDTO> findMissionsByAgencyName(@Param("agencyName") String agencyName);
}
