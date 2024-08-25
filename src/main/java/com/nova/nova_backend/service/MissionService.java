package com.nova.nova_backend.service;

import com.nova.nova_backend.domain.dto.AgencyMissionDTO;
import com.nova.nova_backend.domain.entity.*;
import com.nova.nova_backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final MissionRepository missionRepository;
    private final AgencyPointRepository agencyPointRepository;
    private final AgencyItemRepository agencyItemRepository;
    private final AgencyPointHistoryRepository agencyPointHistoryRepository;
    private final AgencyRepository agencyRepository;

    public List<AgencyMissionDTO> getAgencyMissionListByAgencyName(String agencyName) {
        // return missionRepository.findMissionsByAgencyName(agencyName);
        return null;
    }

    @Transactional
    public String insertAgencyMission(Map<String, Object> requestMap) {

        String agencyCode = String.valueOf(requestMap.getOrDefault("agencyCode", ""));

        // 사용 가능 포인트 조회
        AgencyPoint agencyPoint = agencyPointRepository.findByAgencyCode(agencyCode);

        // T_AGENCY_ITEM 조회 (대행사 지정 단가 조회)
        String reward = String.valueOf(requestMap.getOrDefault("reward", ""));
        String itemName = String.valueOf(requestMap.getOrDefault("itemName", ""));
        AgencyItem agencyItem = agencyItemRepository.findByAgencyCodeAndRewardAndItemName(agencyCode, reward, itemName);

        // 잔액이 없을 경우
        if (agencyPoint == null || agencyPoint.getAvailablePoints().compareTo(agencyItem.getItemPrice()) <= 0) {
            return "NO POINTS";
        }

        // Agency 객체 조회
        Agency agency = agencyRepository.findByAgencyCode(agencyCode);

        // MISSION_NO 채번 (날짜 + 증가하는 숫자)
        String missionNo = generateMissionNo();

        AgencyMission agencyMission = new AgencyMission();
        agencyMission.setMissionNo(missionNo);
        agencyMission.setAgency(agency);
        agencyMission.setReward(reward);
        agencyMission.setItemName(itemName);
        agencyMission.setMid(String.valueOf(requestMap.getOrDefault("mid", "")));

        agencyMission.setPlaceName(String.valueOf(requestMap.getOrDefault("placeName", "")));
        agencyMission.setPlaceUrl(String.valueOf(requestMap.getOrDefault("placeUrl", "")));
        agencyMission.setPriceComparisonId(String.valueOf(requestMap.getOrDefault("priceComparisonId", "")));
        agencyMission.setMainSearchKeyword(String.valueOf(requestMap.getOrDefault("mainSearchKeywords", "")));
        agencyMission.setSubSearchKeyword(String.valueOf(requestMap.getOrDefault("subSearchKeyword", "")));
        agencyMission.setRankKeyword(String.valueOf(requestMap.getOrDefault("rankKeyword", "")));
        agencyMission.setCorrectAnswer(String.valueOf(requestMap.getOrDefault("correctAnswer", "")));

        // String을 Date로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            // 문자열을 LocalDate로 변환
            LocalDate localAdStartDate = LocalDate.parse(String.valueOf(requestMap.getOrDefault("adStartDate", "")), formatter);
            LocalDate localAdEndDate = LocalDate.parse(String.valueOf(requestMap.getOrDefault("adEndDate", "")), formatter);

            // LocalDate를 Date로 변환
            Date adStartDate = Date.from(localAdStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date adEndDate = Date.from(localAdEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            agencyMission.setAdStartDate(adStartDate);
            agencyMission.setAdEndDate(adEndDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        agencyMission.setAdRequestDate(new Date());

        agencyMission.setTotalRequest(Integer.parseInt(String.valueOf(requestMap.getOrDefault("totalRequest", "0"))));
        agencyMission.setDailyWorkload(Integer.parseInt(String.valueOf(requestMap.getOrDefault("dailyWorkload", "0"))));
        agencyMission.setTotalWorkdays(Integer.parseInt(String.valueOf(requestMap.getOrDefault("totalWorkdays", "0"))));

        agencyMission.setMissionStatus(String.valueOf(requestMap.getOrDefault("missionStatus", "")));

        agencyMission = missionRepository.save(agencyMission);

        // HISTORY_NO 채번 (날짜 + 증가하는 숫자)
        String historyNo = generateHistoryNo();

        // T_AGENCY_POINT_HISTORY 저장
        AgencyPointHistory agencyPointHistory = new AgencyPointHistory();
        agencyPointHistory.setPointHistoryNo(historyNo);
        agencyPointHistory.setAgency(agency);
        agencyPointHistory.setMission(agencyMission);
        agencyPointHistory.setContent("Mission Add");
        agencyPointHistory.setPoints(agencyItem.getItemPrice());
        agencyPointHistory.setRegisterDateTime(new Date());
        agencyPointHistory.setStatus("DEDUCTION");

        agencyPointHistoryRepository.save(agencyPointHistory);

        // T_AGENCY_POINT.AVAILABLE_POINTS 차감
        agencyPoint.setAvailablePoints(agencyPoint.getAvailablePoints().subtract(agencyItem.getItemPrice()));
        agencyPointRepository.save(agencyPoint);

        return "SEUCCESS";
    }

    private synchronized String generateMissionNo() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String today = dateFormat.format(new Date());

        String maxMissionNo = missionRepository.findMaxMissionNoStartingWith(today);

        int newCodeNumber = 1; // 기본 시작값

        if (maxMissionNo != null) {
            // 숫자 부분을 추출하여 +1
            String numberPart = maxMissionNo.substring(today.length());
            newCodeNumber = Integer.parseInt(numberPart) + 1;
        }

        // 새로운 코드 생성, 숫자 부분을 8자리로 포맷
        String newMissionNo = String.format("%s%08d", today, newCodeNumber);
        return newMissionNo;
    }

    private synchronized String generateHistoryNo() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String today = dateFormat.format(new Date());

        String maxPointHistoryNo = agencyPointHistoryRepository.findMaxPointHistoryNoStartingWith(today);

        int newCodeNumber = 1; // 기본 시작값

        if (maxPointHistoryNo != null) {
            // 숫자 부분을 추출하여 +1
            String numberPart = maxPointHistoryNo.substring(today.length());
            newCodeNumber = Integer.parseInt(numberPart) + 1;
        }

        // 새로운 코드 생성, 숫자 부분을 8자리로 포맷
        String newPointHistoryNo = String.format("%s%08d", today, newCodeNumber);
        return newPointHistoryNo;
    }
}
