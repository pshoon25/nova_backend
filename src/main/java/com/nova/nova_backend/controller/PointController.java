package com.nova.nova_backend.controller;

import com.nova.nova_backend.domain.dto.AdminMissionDTO;
import com.nova.nova_backend.domain.dto.AgencyPointDTO;
import com.nova.nova_backend.domain.dto.PointHistoryDTO;
import com.nova.nova_backend.domain.entity.Agency;
import com.nova.nova_backend.domain.entity.AgencyPointHistory;
import com.nova.nova_backend.service.PointService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.RichTextString;
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
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/pointHistoryExcelDownload")
    public ResponseEntity<?> pointHistoryExcelDownload(@RequestParam(value = "agencyName", required = false) String agencyName,
                                                       @RequestParam(value = "status", required = false) String status) throws IOException {

        // 포인트 사용 내역 리스트를 가져오는 서비스 호출
        List<PointHistoryDTO> pointHistoryList = pointService.getPointHistoryListByAgencyName(agencyName, status);

        // 엑셀 파일 생성
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Point History List");

            // 헤더 행 생성
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("대행사명");
            headerRow.createCell(1).setCellValue("이력번호");
            headerRow.createCell(2).setCellValue("리워드");
            headerRow.createCell(3).setCellValue("미션번호");
            headerRow.createCell(4).setCellValue("내역");
            headerRow.createCell(5).setCellValue("포인트");
            headerRow.createCell(6).setCellValue("일자");
            headerRow.createCell(7).setCellValue("비고");
            headerRow.createCell(8).setCellValue("입금자명");

            // 데이터 행 생성
            int rowNum = 1;
            for (PointHistoryDTO pointHistoryDTO : pointHistoryList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(pointHistoryDTO.getAgencyName());
                row.createCell(1).setCellValue(pointHistoryDTO.getPointHistoryNo());
                row.createCell(2).setCellValue(pointHistoryDTO.getReward());
                row.createCell(3).setCellValue(pointHistoryDTO.getMissionNo());
                row.createCell(4).setCellValue(pointHistoryDTO.getContent());
                row.createCell(5).setCellValue(pointHistoryDTO.getPoints().doubleValue());
                row.createCell(6).setCellValue(pointHistoryDTO.getRegisterDateTime());
                row.createCell(7).setCellValue(pointHistoryDTO.getStatus());
                row.createCell(8).setCellValue(pointHistoryDTO.getDepositor());
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

    @GetMapping("/getPointHistoryListByAgencyName")
    public List<PointHistoryDTO> getPointHistoryListByAgencyName(@RequestParam(value = "agencyName", required = false) String agencyName,
                                                                 @RequestParam(value = "status", required = false) String status) {
        return pointService.getPointHistoryListByAgencyName(agencyName, status);
    }

    @GetMapping("/getPointHistoryListByAgencyCode")
    public List<PointHistoryDTO> getPointHistoryListByAgencyCode(@RequestParam(value = "agencyCode", required = false) String agencyCode,
                                                                 @RequestParam(value = "status", required = false) String status) {
        return pointService.getPointHistoryListByAgencyCode(agencyCode, status);
    }

    @GetMapping("/getAgencyPointByAgencyName")
    public AgencyPointDTO getAgencyPointByAgencyName(@RequestParam("agencyName") String agencyName) {
        return pointService.getAgencyPointByAgencyName(agencyName);
    }

    @GetMapping("/getAgencyPointByAgencyCode")
    public AgencyPointDTO getAgencyPointByAgencyCode(@RequestParam("agencyCode") String agencyCode) {
        return pointService.getAgencyPointByAgencyCode(agencyCode);
    }

    @PostMapping("/requestPointRecharge")
    public String requestPointRecharge(@RequestBody Map<String, Object> requestMap) {
        return pointService.requestPointRecharge(requestMap);
    }

    @PostMapping("/approveRecharge")
    public String approveRecharge(@RequestBody Map<String, Object> requestMap) {
        return pointService.approveRecharge(requestMap);
    }
}
