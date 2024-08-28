package com.nova.nova_backend.repository;

import com.nova.nova_backend.domain.entity.AgencyDepositInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyDepositInfoRepository extends JpaRepository<AgencyDepositInfo, Long> {
}
