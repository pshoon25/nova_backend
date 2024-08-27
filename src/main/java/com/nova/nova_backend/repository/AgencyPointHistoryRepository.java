package com.nova.nova_backend.repository;

import com.nova.nova_backend.domain.dto.PointHistoryDTO;
import com.nova.nova_backend.domain.entity.AgencyPointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgencyPointHistoryRepository extends JpaRepository<AgencyPointHistory, Long> {
    @Query("SELECT new com.nova.nova_backend.domain.dto.PointHistoryDTO(p.pointHistoryNo, m.reward, a.agencyName, m.missionNo, p.content, p.points, p.registerDateTime, p.status) " +
            "FROM AgencyPointHistory p " +
            "LEFT JOIN p.agency a " +
            "LEFT JOIN p.mission m " +
            "WHERE (:agencyName IS NULL OR a.agencyName LIKE %:agencyName%) AND " +
            "(:status IS NULL OR p.status = :status)")
    List<PointHistoryDTO> findPointHistoryDetails(@Param("agencyName") String agencyName,
                                                  @Param("status") String status);

    @Query("SELECT MAX(a.pointHistoryNo) FROM AgencyPointHistory a WHERE a.pointHistoryNo LIKE :prefix%")
    String findMaxPointHistoryNoStartingWith(@Param("prefix") String prefix);
}
