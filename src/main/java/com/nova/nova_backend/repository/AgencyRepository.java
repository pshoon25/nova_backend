package com.nova.nova_backend.repository;

import com.nova.nova_backend.domain.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
}
