package com.nova.nova_backend.repository;

import com.nova.nova_backend.domain.entity.AgencySalt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencySaltRepository extends JpaRepository<AgencySalt, Long> {
    AgencySalt findByAgencyCode(String agencyCode);
}
