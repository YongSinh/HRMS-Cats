package com.cats.payrollservice.controller;

import com.cats.payrollservice.service.PayslipReportService;
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
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
public class ReportController {
    private final PayslipReportService payslipReportService;

    @GetMapping("/report/payslip")
    public ResponseEntity<?> getUserAttendangetPayslipForEmpceReport(@RequestParam(name = "refNo") String refNo) throws JRException, IOException {
        byte[] reportContent = payslipReportService.getPayslipForEmp(refNo);

        ByteArrayResource arrayResource = new ByteArrayResource(reportContent);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(arrayResource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.inline()
                                .filename("payslip.pdf")
                                .build().toString())
                .body(arrayResource);
    }


    @GetMapping("/v2/report/payslip")
    public ResponseEntity<?> getPayslipForFirstAndSecondPayments(@RequestParam(name = "refNo") String refNo) throws JRException, IOException {
        byte[] reportContent = payslipReportService.getPayslipForFirstAndSecondPayments(refNo);

        ByteArrayResource arrayResource = new ByteArrayResource(reportContent);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(arrayResource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.inline()
                                .filename("payslip.pdf")
                                .build().toString())
                .body(arrayResource);
    }


    @GetMapping("/report/listPayslip")
    public ResponseEntity<?> getlistPayslip(@RequestParam(name = "emId") Long emId) throws JRException, IOException {
        byte[] reportContent = payslipReportService.getPayslipListReport(emId);

        ByteArrayResource arrayResource = new ByteArrayResource(reportContent);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(arrayResource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.inline()
                                .filename("payslip-list.pdf")
                                .build().toString())
                .body(arrayResource);
    }

    @GetMapping("/report/listPayslipByDate")
    public ResponseEntity<?> getlistPayslipByDate(@RequestParam(name = "emId") Long emId, @RequestParam(name = "date") LocalDate date) throws JRException, IOException {
        byte[] reportContent = payslipReportService.getPayslipReportByDate(date, emId);
        ByteArrayResource arrayResource = new ByteArrayResource(reportContent);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(arrayResource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.inline()
                                .filename("payslip-list.pdf")
                                .build().toString())
                .body(arrayResource);
    }
}
