package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.model.Payslip;
import com.cats.payrollservice.service.PayrollService;
import com.cats.payrollservice.service.PayslipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
public class PayslipController {

    private final PayslipService payslipService;

    @GetMapping("/payslips")
    public BaseApi<?> getListPayrollById() {
        List<Payslip> payslips = payslipService.getListPaySlip();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payslips have been found")
                .timestamp(LocalDateTime.now())
                .data(payslips)
                .build();
    }
    @PostMapping("/payslips/add")
    public BaseApi<?> getListPayrollById(@RequestPart("body") PayslipReqDto payslipReqDto, @RequestPart("emId")List<Long> emId) {
        List<Payslip> payslips = payslipService.create(payslipReqDto,emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payslips have been created")
                .timestamp(LocalDateTime.now())
                .data(payslips)
                .build();
    }
}
