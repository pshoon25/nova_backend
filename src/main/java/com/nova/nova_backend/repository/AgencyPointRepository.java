package com.nova.nova_backend.repository;

import com.nova.nova_backend.domain.entity.AgencyPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.nova.nova_backend.domain.dto.AgencyPointDTO;

import java.util.List;

public interface AgencyPointRepository extends JpaRepository<AgencyPoint, Long> {
    // @Query("SELECT new com.nova.nova_backend.domain.dto.AgencyPointDTO(p.availablePoints) " +
    //         "FROM AgencyPoint p " +
    //         "LEFT JOIN Agency a " +
    //         "WHERE a.agencyName LIKE %:agencyName%")
    // List<AgencyPointDTO> findPointsByAgencyName(@Param("agencyName") String agencyName);
}
