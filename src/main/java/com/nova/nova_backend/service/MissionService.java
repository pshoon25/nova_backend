package com.nova.nova_backend.service;

import com.nova.nova_backend.domain.dto.AdminMissionDTO;
import com.nova.nova_backend.domain.entity.*;
import com.nova.nova_backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
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

    public List<AdminMissionDTO> getAgencyMissionListByAgencyName(String agencyName, String reward, String itemName) {
         return missionRepository.findMissionsByAgencyName(agencyName, reward, itemName);
    }

    public List<AgencyMission> getAgencyMissionListByAgencyCode(String agencyCode, String reward, String placeName, String itemName) {
        return missionRepository.findMissionsByAgencyCode(agencyCode, reward, placeName, itemName);
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

        // String을 Date로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date adStartDate = new Date();
        Date adEndDate = new Date();

        try {
            // 문자열을 LocalDate로 변환
            LocalDate localAdStartDate = LocalDate.parse(String.valueOf(requestMap.getOrDefault("adStartDate", "")), formatter);
            LocalDate localAdEndDate = LocalDate.parse(String.valueOf(requestMap.getOrDefault("adEndDate", "")), formatter);

            // LocalDate를 Date로 변환
            adStartDate = Date.from(localAdStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            adEndDate = Date.from(localAdEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 날짜 차이 계산 (일수)
        long duration = adEndDate.getTime() - adStartDate.getTime();

        // 총 요청일 수
        long totalWorkdays = (duration / (1000 * 60 * 60 * 24)) + 1; // 밀리초를 일수로 변환

        // 일일 작업량 계산
        int dailyWorkload = Integer.parseInt(String.valueOf(requestMap.getOrDefault("dailyWorkload", 0)));

        // 총 요청량
        int totalRequest = (int) (totalWorkdays * dailyWorkload);

        // 차감할 포인트 계산
        BigDecimal deductionPoints = agencyItem.getItemPrice().multiply(BigDecimal.valueOf(totalWorkdays)).multiply(BigDecimal.valueOf(dailyWorkload));


        // 잔액이 없을 경우
        if (agencyPoint == null || agencyPoint.getAvailablePoints().compareTo(deductionPoints) <= 0) {
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
        agencyMission.setMainSearchKeyword(String.valueOf(requestMap.getOrDefault("mainSearchKeyword", "")));
        agencyMission.setSubSearchKeyword(String.valueOf(requestMap.getOrDefault("subSearchKeyword", "")));
        agencyMission.setRankKeyword(String.valueOf(requestMap.getOrDefault("rankKeyword", "")));
        agencyMission.setCorrectAnswer(String.valueOf(requestMap.getOrDefault("correctAnswer", "")));
        agencyMission.setAdStartDate(adStartDate);
        agencyMission.setAdEndDate(adEndDate);

        agencyMission.setAdRequestDate(new Date());

        agencyMission.setTotalRequest(totalRequest);
        agencyMission.setDailyWorkload(Integer.parseInt(String.valueOf(requestMap.getOrDefault("dailyWorkload", 0))));
        agencyMission.setTotalWorkdays((int) totalWorkdays);

        agencyMission.setMissionStatus("WATING");

        agencyMission = missionRepository.save(agencyMission);

        // HISTORY_NO 채번 (날짜 + 증가하는 숫자)
        String historyNo = generateHistoryNo();

        // T_AGENCY_POINT_HISTORY 저장
        AgencyPointHistory agencyPointHistory = new AgencyPointHistory();
        agencyPointHistory.setPointHistoryNo(historyNo);
        agencyPointHistory.setAgency(agency);
        agencyPointHistory.setMission(agencyMission);
        agencyPointHistory.setContent("Mission Add");
        agencyPointHistory.setPoints(deductionPoints);
        agencyPointHistory.setRegisterDateTime(new Date());
        agencyPointHistory.setStatus("DEDUCTION");

        agencyPointHistoryRepository.save(agencyPointHistory);

        // T_AGENCY_POINT.AVAILABLE_POINTS 차감
        agencyPoint.setAvailablePoints(agencyPoint.getAvailablePoints().subtract(deductionPoints));
        agencyPointRepository.save(agencyPoint);

        return "SUCCESS";
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

    public synchronized String generateHistoryNo() {
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

    @Transactional
    public String saveNovaMissionStatus(List<Map<String, Object>> requestMapList) {
        try {
            for (Map<String, Object> map : requestMapList) {
                String missionNo = String.valueOf(map.get("missionNo"));
                String missionStatus = String.valueOf(map.get("missionStatus"));

                if (missionNo != null && missionStatus != null) {
                    if ("CANCEL".equals(missionStatus)) {
                        // 미션 정보 조회
                        AgencyMission agencyMission = missionRepository.findByMissionNo(missionNo);

                        // 금액 조회
                        AgencyItem agencyItem = agencyItemRepository.findByAgencyCodeAndRewardAndItemName(agencyMission.getAgency().getAgencyCode(), agencyMission.getReward(), agencyMission.getItemName());

                        // 남은 일자 계산
                        LocalDate today = LocalDate.now();
                        Date adEndDate = agencyMission.getAdEndDate(); // 미션 종료일 가져오기

                        LocalDate adEndLocalDate = new java.sql.Date(adEndDate.getTime()).toLocalDate();

                        long remainingDays = ChronoUnit.DAYS.between(today, adEndLocalDate);

                        // 환급할 포인트 계산
                        BigDecimal refundPoints = null;
                        if (remainingDays > 0) {
                            refundPoints = agencyItem.getItemPrice().multiply(BigDecimal.valueOf(remainingDays)).multiply(BigDecimal.valueOf(agencyMission.getDailyWorkload()));
                        }

                        // HISTORY_NO 채번 (날짜 + 증가하는 숫자)
                        String historyNo = generateHistoryNo();

                        // T_AGENCY_POINT_HISTORY 저장
                        AgencyPointHistory agencyPointHistory = new AgencyPointHistory();
                        agencyPointHistory.setPointHistoryNo(historyNo);
                        agencyPointHistory.setAgency(agencyMission.getAgency());
                        agencyPointHistory.setMission(agencyMission);
                        agencyPointHistory.setContent("Mission Cancel");
                        agencyPointHistory.setPoints(refundPoints);
                        agencyPointHistory.setRegisterDateTime(new Date());
                        agencyPointHistory.setStatus("REFUND");

                        agencyPointHistoryRepository.save(agencyPointHistory);

                        // 환급 포인트 추가
                        AgencyPoint agencyPoint = agencyPointRepository.findByAgencyCode(agencyMission.getAgency().getAgencyCode());
                        agencyPoint.setAvailablePoints(agencyPoint.getAvailablePoints().add(refundPoints));
                        agencyPointRepository.save(agencyPoint);
                    }

                    // 미션 상태 업데이트
                    missionRepository.updateMissionStatus(missionNo, missionStatus);
                }
            }
            return "SUCCESS";
        } catch (Exception e) {
            System.err.println("Error updating mission statuses: " + e.getMessage());
            return "FAIL";
        }
    }
}
