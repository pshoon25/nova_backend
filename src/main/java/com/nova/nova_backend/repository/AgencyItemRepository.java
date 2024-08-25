package com.nova.nova_backend.repository;


import com.nova.nova_backend.domain.entity.Agency;
import com.nova.nova_backend.domain.entity.AgencyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgencyItemRepository extends JpaRepository<AgencyItem, Long> {

    @Query("SELECT ai FROM AgencyItem ai WHERE ai.agency = :agency AND ai.reward = :reward")
    List<AgencyItem> findByAgencyCodeAndReward(@Param("agency")Agency agency,
                                               @Param("reward") String reward);

    @Query("SELECT ai FROM AgencyItem ai WHERE ai.agency.agencyCode = :agencyCode AND ai.reward = :reward AND ai.itemName = :itemName")
    AgencyItem findByAgencyCodeAndRewardAndItemName(@Param("agencyCode") String agencyCode,
                                                    @Param("reward") String reward,
                                                    @Param("itemName") String itemName);
}