package com.orummmedia.orummmedia_backend.repository;

import com.orummmedia.orummmedia_backend.domain.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
}
