package com.nova.nova_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nova.nova_backend.domain.dto.AdminMissionDTO;
import com.nova.nova_backend.domain.entity.AgencyMission;
import com.nova.nova_backend.service.MissionService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mission")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/missionExcelDownload")
    public ResponseEntity<?> missionExcelDownload(
            @RequestParam("agencyName") String agencyName,
            @RequestParam("reward") String reward,
            @RequestParam("itemName") String itemName) throws IOException {

        // 미션 리스트를 가져오는 서비스 호출
        List<AdminMissionDTO> missionList = missionService.getAgencyMissionListByAgencyName(agencyName, reward, itemName);

        // 엑셀 파일 생성
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Mission List");

            if ("NOVA".equals(reward)) {
                // 헤더 행 생성
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("미션번호");
                headerRow.createCell(1).setCellValue("대행사");
                headerRow.createCell(2).setCellValue("종류");
                headerRow.createCell(3).setCellValue("유형");
                headerRow.createCell(4).setCellValue("MID");
                headerRow.createCell(5).setCellValue("가격비교 ID");
                headerRow.createCell(6).setCellValue("광고 시작일");
                headerRow.createCell(7).setCellValue("1일 작업량");
                headerRow.createCell(8).setCellValue("총 작업일수");
                headerRow.createCell(9).setCellValue("업체명");
                headerRow.createCell(10).setCellValue("순위키워드");
                headerRow.createCell(11).setCellValue("메인검색 키워드");
                headerRow.createCell(12).setCellValue("3위이내검색 키워드");
                headerRow.createCell(13).setCellValue("광고종료일");
                headerRow.createCell(14).setCellValue("플레이스주소");
                headerRow.createCell(15).setCellValue("총요청량");
                headerRow.createCell(16).setCellValue("미션상태");

                // 데이터 행 생성
                int rowNum = 1;
                for (AdminMissionDTO mission : missionList) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(mission.getMissionNo());
                    row.createCell(1).setCellValue(mission.getAgencyName());
                    row.createCell(2).setCellValue(mission.getItemName().startsWith("PLACE") ? "PLACE" : mission.getItemName().startsWith("SMARTSTORE") ? "SMARTSTORE" : "");
                    row.createCell(3).setCellValue(mission.getItemName());
                    row.createCell(4).setCellValue(mission.getMid());
                    row.createCell(5).setCellValue(mission.getPriceComparisonId());
                    row.createCell(6).setCellValue(mission.getAdStartDate());
                    row.createCell(7).setCellValue(mission.getDailyWorkload());
                    row.createCell(8).setCellValue(mission.getTotalWorkdays());
                    row.createCell(9).setCellValue(mission.getPlaceName());
                    row.createCell(10).setCellValue(mission.getRankKeyword());
                    row.createCell(11).setCellValue(mission.getMainSearchKeyword());
                    row.createCell(12).setCellValue(mission.getSubSearchKeyword());
                    row.createCell(13).setCellValue(mission.getAdEndDate());
                    row.createCell(14).setCellValue(mission.getPlaceUrl());
                    row.createCell(15).setCellValue(mission.getTotalRequest());
                    row.createCell(16).setCellValue(mission.getMissionStatus());
                }
            } else if ("OLOCK".equals(reward)) {
                // 헤더 행 생성
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("미션번호");
                headerRow.createCell(1).setCellValue("대행사");
                headerRow.createCell(2).setCellValue("종류");
                headerRow.createCell(3).setCellValue("MID");
                headerRow.createCell(4).setCellValue("가격비교 ID");
                headerRow.createCell(5).setCellValue("광고 시작일");
                headerRow.createCell(6).setCellValue("1일 작업량");
                headerRow.createCell(7).setCellValue("총 작업일수");
                headerRow.createCell(8).setCellValue("업체명");
                headerRow.createCell(9).setCellValue("업체명구분용");
                headerRow.createCell(10).setCellValue("메인검색 키워드");
                headerRow.createCell(11).setCellValue("정답 (주변- 명소 1번째)");
                headerRow.createCell(12).setCellValue("광고종료일");
                headerRow.createCell(13).setCellValue("플레이스주소");
                headerRow.createCell(14).setCellValue("총요청량");
                headerRow.createCell(15).setCellValue("미션상태");

                // 데이터 행 생성
                int rowNum = 1;
                for (AdminMissionDTO mission : missionList) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(mission.getMissionNo());
                    row.createCell(1).setCellValue(mission.getAgencyName());
                    row.createCell(2).setCellValue(mission.getItemName());
                    row.createCell(3).setCellValue(mission.getMid());
                    row.createCell(4).setCellValue(mission.getPriceComparisonId());
                    row.createCell(5).setCellValue(mission.getAdStartDate());
                    row.createCell(6).setCellValue(mission.getDailyWorkload());
                    row.createCell(7).setCellValue(mission.getTotalWorkdays());
                    row.createCell(8).setCellValue(mission.getPlaceName());
                    row.createCell(9).setCellValue("");
                    row.createCell(10).setCellValue(mission.getMainSearchKeyword());
                    row.createCell(11).setCellValue(mission.getCorrectAnswer());
                    row.createCell(12).setCellValue(mission.getAdEndDate());
                    row.createCell(13).setCellValue(mission.getPlaceUrl());
                    row.createCell(14).setCellValue(mission.getTotalRequest());
                    row.createCell(15).setCellValue(mission.getMissionStatus());
                }
            }

            // 엑셀 파일을 바이트 배열로 변환
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            // HTTP 응답 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mission_list.xlsx");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(outputStream.toByteArray());
        } catch (IOException e) {
            // 예외 처리: 적절한 오류 응답을 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("엑셀 파일 생성 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/getAgencyMissionListByAgencyName")
    public List<AdminMissionDTO> getAgencyMissionListByAgencyName(@RequestParam("agencyName") String agencyName,
                                                                  @RequestParam("reward") String reward,
                                                                  @RequestParam("itemName") String itemName){
        return missionService.getAgencyMissionListByAgencyName(agencyName, reward, itemName);
    }

    @GetMapping("/getAgencyMissionListByAgencyCode")
    public List<AgencyMission> getAgencyMissionListByAgencyCode(@RequestParam("agencyCode") String agencyCode,
                                                                @RequestParam("reward") String reward,
                                                                @RequestParam("placeName") String placeName,
                                                                @RequestParam("itemName") String itemName){
        return missionService.getAgencyMissionListByAgencyCode(agencyCode, reward, placeName, itemName);
    }

    @PostMapping("/addMissions")
    public String insertAgencyMission(@RequestBody Map<String, Object> requestMap) {
        return missionService.insertAgencyMission(requestMap);
    }

    @PostMapping("/saveMissionInfo")
    public String saveMissionInfo(@RequestBody List<Map<String, Object>> requestMapList) throws Exception {
        return missionService.saveMissionInfo(requestMapList);
    }
}