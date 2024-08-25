package com.nova.nova_backend.service;

import com.nova.nova_backend.converter.Encrypt;
import com.nova.nova_backend.domain.dto.AgencyItemSummaryDTO;
import com.nova.nova_backend.domain.entity.Agency;
import com.nova.nova_backend.domain.entity.AgencyItem;
import com.nova.nova_backend.domain.entity.AgencySalt;
import com.nova.nova_backend.repository.AgencyItemRepository;
import com.nova.nova_backend.repository.AgencyRepository;
import com.nova.nova_backend.repository.AgencySaltRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AgencyService {
    private final AgencyRepository agencyRepository;
    private final AgencyItemRepository agencyItemRepository;
    private final AgencySaltRepository agencySaltRepository;

    private final Encrypt encrypt;

    public int checkLoginIdDuplicate(String loginId) {
        // 로그인 ID로 Agency 조회
        Agency agency = agencyRepository.findByLoginId(loginId);

        // 로그인 ID가 존재하면 1, 존재하지 않으면 0 반환
        return (agency != null) ? 1 : 0;
    }

    public List<AgencyItemSummaryDTO> getAgencyList(String agencyName, String resaleYn, String useYn) {
        return agencyRepository.findAgencyItems(agencyName, resaleYn, useYn);
    }

    @Transactional
    public Agency insertAgencyInfo(Map<String, Object> requestData) {
        Agency agency = new Agency();

        // AGENCY_CODE 생성
        String agencyCode = generateAgencyCode(); // 새로운 AGENCY_CODE 생성

        // 비밀번호 암호화
        String salt = encrypt.getSalt();
        String encodedPw = encrypt.getEncrypt(String.valueOf(requestData.get("password")), salt);

        // 기본 정보 저장
        agency.setAgencyCode(agencyCode);
        agency.setLoginId((String) requestData.get("loginId"));
        agency.setPassword(encodedPw);
        agency.setAgencyName((String) requestData.get("agencyName"));
        agency.setName((String) requestData.get("name"));
        agency.setPhoneNum((String) requestData.get("phoneNum"));
        agency.setResaleYn((String) requestData.get("resaleYn"));
        agency.setUserType("AGENCY");
        agency.setUseYn("Y");
        agency.setRegisterDateTime(LocalDate.now());
        agencyRepository.save(agency);

        // salt 정보 저장
        AgencySalt agencySalt = new AgencySalt();
        agencySalt.setAgencyCode(agencyCode);
        agencySalt.setSalt(salt);
        agencySaltRepository.save(agencySalt);

        // NOVA 아이템 저장
        Map<String, String> novaItems = (Map<String, String>) ((Map<String, Object>) requestData.get("items")).get("NOVA");
        if (novaItems != null) {
            saveAgencyItems(agency, "NOVA", novaItems);
        }

        // OLOCK 아이템 저장
        Map<String, String> olockItems = (Map<String, String>) ((Map<String, Object>) requestData.get("items")).get("OLOCK");
        if (olockItems != null) {
            saveAgencyItems(agency, "OLOCK", olockItems);
        }
        return agency;
    }

    public String generateAgencyCode() {
        // 데이터베이스에서 가장 큰 AGENCY_CODE 값을 가져옴
        String maxAgencyCode = agencyRepository.findMaxAgencyCode();

        String prefix = "NOVA_AGE";
        int newCodeNumber = 1; // 기본 시작값

        if (maxAgencyCode != null) {
            // 숫자 부분을 추출하여 +1
            String numberPart = maxAgencyCode.substring(prefix.length());
            newCodeNumber = Integer.parseInt(numberPart) + 1;
        }

        // 새로운 코드 생성, 숫자 부분을 8자리로 포맷
        String newAgencyCode = String.format("%s%08d", prefix, newCodeNumber);
        return newAgencyCode;
    }

    private void saveAgencyItems(Agency agency, String reward, Map<String, String> items) {
        for (Map.Entry<String, String> entry : items.entrySet()) {
            AgencyItem agencyItem = new AgencyItem();
            agencyItem.setAgency(agency);
            agencyItem.setReward(reward);
            agencyItem.setItemName(entry.getKey());
            agencyItem.setItemPrice(new BigDecimal(entry.getValue()));
            agencyItem.setUseYn("Y");
            agencyItemRepository.save(agencyItem);
        }
    }

    @Transactional
    public int updateAgencyInfo(List<AgencyItemSummaryDTO> agencyItemSummaryDTOList) {
        int updatedCount = 0;

        for (AgencyItemSummaryDTO dto : agencyItemSummaryDTOList) {
            // Retrieve existing agency from the repository
            Agency agency = agencyRepository.findByAgencyCode(dto.getAgencyCode());

            agency.setAgencyName(agency.getAgencyName().equals(dto.getAgencyName()) ? agency.getAgencyName() : dto.getAgencyName());
            agency.setName(agency.getName().equals(dto.getName()) ? agency.getName() : dto.getName());
            agency.setPhoneNum(agency.getPhoneNum().equals(dto.getPhoneNum()) ? agency.getPhoneNum() : dto.getPhoneNum());
            agency.setResaleYn(agency.getResaleYn().equals(dto.getResaleYn()) ? agency.getResaleYn() : dto.getResaleYn());
            agency.setUseYn(agency.getUseYn().equals(dto.getUseYn()) ? agency.getUseYn() : dto.getUseYn());

            // Save updated agency
            agencyRepository.save(agency);
            updatedCount++;

            // T_AGENCY_ITEM 업데이트
            saveAgencyItem(dto, agency, "NOVA");
            saveAgencyItem(dto, agency, "OLOCK");
        }

        return updatedCount;
    }

    private void saveAgencyItem(AgencyItemSummaryDTO dto, Agency agency, String reward) {
        List<AgencyItem> agencyitemList = agencyItemRepository.findByAgencyCodeAndReward(agency, reward);

        for (AgencyItem agencyitem : agencyitemList) {
            if ("NOVA".equals(reward)) {
                if ("PLACE_SEARCH".equals(agencyitem.getItemName())) {
                    agencyitem.setItemPrice(dto.getNovaPlaceSearch());
                } else if ("PLACE_SEARCH_SAVE".equals(agencyitem.getItemName())) {
                    agencyitem.setItemPrice(dto.getNovaPlaceSearchSave());
                } else if ("PLACE_SEARCH_SAVE_PREMIUM".equals(agencyitem.getItemName())) {
                    agencyitem.setItemPrice(dto.getNovaPlaceSearchSavePremium());
                } else if ("PLACE_KEEP".equals(agencyitem.getItemName())) {
                    agencyitem.setItemPrice(dto.getNovaPlaceKeep());
                } else if ("SMARTSTORE_SEARCH".equals(agencyitem.getItemName())) {
                    agencyitem.setItemPrice(dto.getNovaSmartstoreSearch());
                }
            } else if ("OLOCK".equals(reward)) {
                if ("PLACE_SEARCH".equals(agencyitem.getItemName())) {
                    agencyitem.setItemPrice(dto.getOlockPlaceSearch());
                } else if ("PLACE_SEARCH_SAVE".equals(agencyitem.getItemName())) {
                    agencyitem.setItemPrice(dto.getOlockPlaceSearchSave());
                }
                agencyItemRepository.save(agencyitem);
            }
        }
    }
}

