package com.nova.nova_backend.scheduler;

import com.nova.nova_backend.domain.entity.AgencyMission;
import com.nova.nova_backend.repository.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {

    // 매일 자정에 실행
    @Autowired
    private MissionRepository missionRepository;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void updateMissionStatuses() {
        Date today = new Date();

        List<AgencyMission> missions = missionRepository.findAll();

        for (AgencyMission mission : missions) {
            if (!"CANCEL".equals(mission.getMissionStatus())) {
                if (mission.getAdStartDate().compareTo(today) <= 0
                        && mission.getAdEndDate().compareTo(today) >= 0) {
                    if (!"PROGRESS".equals(mission.getMissionStatus())) {
                        mission.setMissionStatus("PROGRESS");
                        missionRepository.save(mission);
                    }
                } else if (mission.getAdEndDate().compareTo(today) < 0) {
                    if (!"COMPLETED".equals(mission.getMissionStatus())) {
                        mission.setMissionStatus("COMPLETED");
                        missionRepository.save(mission);
                    }
                }
            }
        }
    }
}
