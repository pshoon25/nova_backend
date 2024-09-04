package com.nova.nova_backend.service;

import com.nova.nova_backend.domain.dto.AgencyPointDTO;
import com.nova.nova_backend.domain.dto.PointHistoryDTO;
import com.nova.nova_backend.domain.entity.Agency;
import com.nova.nova_backend.domain.entity.AgencyDepositInfo;
import com.nova.nova_backend.domain.entity.AgencyPoint;
import com.nova.nova_backend.domain.entity.AgencyPointHistory;
import com.nova.nova_backend.repository.AgencyDepositInfoRepository;
import com.nova.nova_backend.repository.AgencyPointHistoryRepository;
import com.nova.nova_backend.repository.AgencyPointRepository;
import com.nova.nova_backend.repository.AgencyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PointService {

    private final AgencyPointHistoryRepository agencyPointHistoryRepository;
    private final AgencyPointRepository agencyPointRepository;
    private final AgencyRepository agencyRepository;
    private final MissionService missionService;
    private final AgencyDepositInfoRepository agencyDepositInfoRepository;

    public List<PointHistoryDTO> getPointHistoryListByAgencyName(String agencyName, String status) {
        return agencyPointHistoryRepository.getPointHistoryListByAgencyName(agencyName, status);
    }

    public List<PointHistoryDTO> getPointHistoryListByAgencyCode(String agencyCode, String status) {
        return agencyPointHistoryRepository.getPointHistoryListByAgencyCode(agencyCode, status);
    }

    public AgencyPointDTO getAgencyPointByAgencyName(String agencyName) {
        return agencyPointRepository.getAgencyPointByAgencyName(agencyName);
    }

    public AgencyPointDTO getAgencyPointByAgencyCode(String agencyCode) {
        return agencyPointRepository.getAgencyPointByAgencyCode(agencyCode);
    }

    public String requestPointRecharge(Map<String, Object> requestMap) {
        try {
            String agencyCode = (String) requestMap.get("agencyCode");
            Agency agency = agencyRepository.findByAgencyCode(agencyCode);

            AgencyPointHistory agencyPointHistory = new AgencyPointHistory();
            agencyPointHistory.setPointHistoryNo(missionService.generateHistoryNo());
            agencyPointHistory.setAgency(agency);
            agencyPointHistory.setContent("Request For Point Recharge");
            String pointsStr = (String) requestMap.get("points");
            BigDecimal points = new BigDecimal(pointsStr);
            agencyPointHistory.setPoints(points);
            agencyPointHistory.setRegisterDateTime(LocalDateTime.now());
            agencyPointHistory.setStatus("REQUEST");

            agencyPointHistoryRepository.save(agencyPointHistory);

            AgencyDepositInfo agencyDepositInfo = new AgencyDepositInfo();
            agencyDepositInfo.setPointHistoryNo(agencyPointHistory.getPointHistoryNo()); // Set as String or appropriate type
            agencyDepositInfo.setAgency(agency);
            agencyDepositInfo.setDepositor(String.valueOf(requestMap.get("depositorName"))); // Fixed parenthesis

            agencyDepositInfoRepository.save(agencyDepositInfo);

            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return "FAILED";
        }
    }

    @Transactional
    public String approveRecharge(Map<String, Object> requestMap) {
        try {
            // PointHistoryNo를 이용해 T_AGENCY_POINT_HISTORY에서 기록을 조회
            String pointHistoryNo = (String) requestMap.get("pointHistoryNo");
            AgencyPointHistory pointHistory = agencyPointHistoryRepository.findByPointHistoryNo(pointHistoryNo);

            // T_AGENCY_POINT_HISTORY.STATUS 의 상태값을 RECHARGE 로 변경
            pointHistory.setStatus("RECHARGE");
            agencyPointHistoryRepository.save(pointHistory);

            // AgencyPoint 엔티티 조회
            Agency agency = pointHistory.getAgency();
            String agencyCode = agency.getAgencyCode();

            // AgencyPoint 엔티티 조회
            AgencyPoint existingAgencyPoint = agencyPointRepository.findByAgencyCode(agencyCode);

            if (existingAgencyPoint == null) {
                // 엔티티가 없으면 새로 생성
                existingAgencyPoint = new AgencyPoint();
                existingAgencyPoint.setAgency(agency);  // Agency 설정
                existingAgencyPoint.setAvailablePoints(pointHistory.getPoints());
                existingAgencyPoint.setUpdateDateTime(new Date());
            } else {
                // 엔티티가 있으면 업데이트
                BigDecimal updatedPoints = existingAgencyPoint.getAvailablePoints().add(pointHistory.getPoints());
                existingAgencyPoint.setAvailablePoints(updatedPoints);
                existingAgencyPoint.setUpdateDateTime(new Date());
            }

            // 엔티티 저장
            agencyPointRepository.save(existingAgencyPoint);

            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 로그 출력
            return "FAILED";
        }
    }

    private void agencyPointSave(AgencyPoint existingAgencyPoint) {

    }
}
