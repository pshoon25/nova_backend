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
    public ResponseEntity<?> missionExcelDownload(@RequestParam("agencyName") String agencyName,
                                                  @RequestParam("reward") String reward,
                                                  @RequestParam("itemName") String itemName,
                                                  HttpServletResponse response) throws IOException {
        // agencyName이 JSON 문자열로 전달되었을 경우, 이를 다시 객체로 변환
        AdminMissionDTO agency = new ObjectMapper().readValue(agencyName, AdminMissionDTO.class);
        // Default 값 처리
        agencyName = (agencyName == null || agencyName.isEmpty()) ? "" : agencyName;

        // 미션 리스트를 가져오는 서비스 호출
        List<AdminMissionDTO> missionList = missionService.getAgencyMissionListByAgencyName(agencyName, reward, itemName);

        // 엑셀 파일 생성
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Mission List");

            // 헤더 행 생성
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Agency Name");
            headerRow.createCell(1).setCellValue("Reward");
            headerRow.createCell(2).setCellValue("Place Name");
            headerRow.createCell(3).setCellValue("Item Name");

            // 데이터 행 생성
            int rowNum = 1;
            for (AdminMissionDTO mission : missionList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(mission.getAgencyName());
                row.createCell(1).setCellValue(mission.getReward());
                row.createCell(2).setCellValue(mission.getPlaceName());
                row.createCell(3).setCellValue(mission.getItemName());
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

    @PostMapping("/saveNovaMissionStatus")
    public String saveNovaMissionStatus(@RequestBody List<Map<String, Object>> requestMapList) throws Exception {
        return missionService.saveNovaMissionStatus(requestMapList);
    }
}