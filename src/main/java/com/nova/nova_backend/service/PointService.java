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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public List<PointHistoryDTO> getPointHistoryList(String agencyName, String status) {
        return agencyPointHistoryRepository.findPointHistoryDetails(agencyName, status);
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
            agencyPointHistory.setRegisterDateTime(new Date());
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

    public String approveRecharge(Map<String, Object> requestMap) {
        try {
            // PointHistoryNo를 이용해 T_AGENCY_POINT_HISTORY에서 기록을 조회
            String pointHistoryNo = (String) requestMap.get("pointHistoryNo");
            if (pointHistoryNo == null || pointHistoryNo.isEmpty()) {
                throw new IllegalArgumentException("PointHistoryNo is required.");
            }

            AgencyPointHistory pointHistory = agencyPointHistoryRepository.findByPointHistoryNo(pointHistoryNo);
            if (pointHistory == null) {
                throw new IllegalArgumentException("Point history not found for the provided PointHistoryNo.");
            }

            // T_AGENCY_POINT_HISTORY.STATUS 의 상태값을 RECHARGE 로 변경
            pointHistory.setStatus("RECHARGE");
            agencyPointHistoryRepository.save(pointHistory);

            // T_AGENCY_POINT.AVAILABLE_POINTS 조회 후 T_AGENCY_POINT_HISTORY의 POINTS 값을 더한 후 저장
            Agency agency = pointHistory.getAgency();
            if (agency == null) {
                throw new IllegalArgumentException("Agency information is missing in point history.");
            }

            AgencyPoint agencyPoint = agencyPointRepository.findByAgencyCode(agency.getAgencyCode());
            if (agencyPoint == null) {
                throw new IllegalArgumentException("AgencyPoint not found for the provided AgencyCode.");
            }

            BigDecimal updatedPoints = agencyPoint.getAvailablePoints().add(pointHistory.getPoints());
            agencyPoint.setAvailablePoints(updatedPoints);
            agencyPoint.setUpdateDateTime(new Date());
            agencyPointRepository.save(agencyPoint);

            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 로그 출력
            return "FAILED";
        }
    }
}
