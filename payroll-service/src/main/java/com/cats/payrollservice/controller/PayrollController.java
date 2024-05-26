package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.model.Allowances;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;
    @GetMapping("/payroll")
    public BaseApi<?> getListPayrollById() {
        List<Payroll> payrollList = payrollService.getListPayroll();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payroll have been found")
                .timestamp(LocalDateTime.now())
                .data(payrollList)
                .build();
    }
    @GetMapping("/payrollByEmId")
    public BaseApi<?> getListPayrollByEmId(@RequestParam Long emId) {
        List<Payroll> payrollList = payrollService.getListPayRollByEmId(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payroll have been found")
                .timestamp(LocalDateTime.now())
                .data(payrollList)
                .build();
    }

    @GetMapping("/payrollByEmIdAndCreateDate")
    public BaseApi<?> getListPayrollByEmIdAndCreateDate(@RequestParam Long emId, @RequestParam LocalDate date) {
        List<Payroll> payrollList = payrollService.getListPayRollByEmIdAndCreateDate(emId, date);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payroll have been found")
                .timestamp(LocalDateTime.now())
                .data(payrollList)
                .build();
    }

    @GetMapping("/fetchEmployeeIds")
    public BaseApi<?> fetchEmployeeIds(@RequestParam Long depId) {
        List<Payroll> payrollList =  payrollService.findPayRollByDepEmId(depId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payroll have been found")
                .timestamp(LocalDateTime.now())
                .data(payrollList)
                .build();
    }

}
