package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.dto.request.PayrollReqDto;
import com.cats.payrollservice.model.Allowances;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/deletePayroll")
    public BaseApi<?> deletePayroll(@RequestParam Long id) {
        Payroll payroll = payrollService.getPayrollById(id);
        payrollService.deletePayroll(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payroll have been delete`")
                .timestamp(LocalDateTime.now())
                .data(payroll)
                .build();
    }


    @GetMapping("/payrollByEmIdAndCreateDate")
    public BaseApi<?> getListPayrollByEmIdAndCreateDate(@RequestParam Long emId, @RequestParam LocalDate date) {
        Payroll payrollList = payrollService.getPayRollByEmIdAndCreateDate(emId, date);
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
    @PostMapping("/addPayroll")
    public BaseApi<?> addPayroll(@RequestPart("body") PayrollReqDto payrollReqDto,@RequestPart("emIds")List<Long> emIds ) {
        List<Payroll> payrollList =  payrollService.create(payrollReqDto, emIds);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payroll have been found")
                .timestamp(LocalDateTime.now())
                .data(payrollList)
                .build();
    }

    @PutMapping("/updatePayroll")
    public BaseApi<?> updatePayroll(@RequestPart("body") PayrollReqDto payrollReqDto,@RequestPart("id")Long id ) {
        Payroll payrollList =  payrollService.update(id, payrollReqDto);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payroll have been update")
                .timestamp(LocalDateTime.now())
                .data(payrollList)
                .build();
    }


}
