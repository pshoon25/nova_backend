package com.nova.nova_backend.repository;

import com.nova.nova_backend.domain.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
    @Query("SELECT a FROM Agency a WHERE " +
            "(:agencyName IS NULL OR a.agencyName LIKE %:agencyName%) AND " +
            "(:resaleYn IS NULL OR a.resaleYn = :resaleYn) AND " +
            "(:useYn IS NULL OR a.useYn = :useYn)")
    List<Agency> findAllByFilters(
            @Param("agencyName") String agencyName,
            @Param("resaleYn") String resaleYn,
            @Param("useYn") String useYn
    );
}
