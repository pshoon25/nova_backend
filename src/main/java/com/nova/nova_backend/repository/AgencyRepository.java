package com.nova.nova_backend.repository;

import com.nova.nova_backend.domain.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
    @Query("SELECT a FROM Agency a WHERE (:agencyName IS NULL OR a.agencyName = :agencyName)")
    List<Agency> findAllByAgencyName(@Param("agencyName") String agencyName);
}
