package com.cats.attendanceservice.controller;

import com.cats.attendanceservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/attendanceLeave")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    @GetMapping("/report/userAttendance")
    public ResponseEntity<?> getUserAttendanceReport(@RequestParam(name = "startDate") LocalDate startDate,
                                                     @RequestParam(name = "endDate") LocalDate endDate,
                                                     @RequestParam(name = "emId") Long emId
                                                     ) throws JRException, IOException {
        LocalDate localDate = LocalDate.now();
        byte[] reportContent = reportService.getAttendanceReportByEmId(startDate,endDate,emId);

        ByteArrayResource arrayResource = new ByteArrayResource(reportContent);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(arrayResource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.inline()
                                .filename("attendance-report.pdf")
                                .build().toString())
                .body(arrayResource);
    }


    @GetMapping("/report/leaveListDateBetween")
    public ResponseEntity<?> getLeaveAndDateBetween(@RequestParam(name = "startDate") LocalDate startDate,
                                                     @RequestParam(name = "endDate") LocalDate endDate,
                                                     @RequestParam(name = "emId") Long emId
    ) throws JRException, IOException {
        LocalDate localDate = LocalDate.now();
        byte[] reportContent = reportService.getLeaveAndDateBetween(startDate,endDate,emId);

        ByteArrayResource arrayResource = new ByteArrayResource(reportContent);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(arrayResource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.inline()
                                .filename("leave-report.pdf")
                                .build().toString())
                .body(arrayResource);
    }

}
