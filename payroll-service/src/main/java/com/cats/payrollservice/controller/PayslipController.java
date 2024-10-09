package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.model.Payslip;
import com.cats.payrollservice.non_entity_POJO.PayrollAndPaySlip;
import com.cats.payrollservice.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
public class PayslipController {
    private final PayslipService payslipService;
    private final PayslipReportService payslipReportService;
   private final EmployeeAllowancesService employeeAllowancesService;
   private final EmployeeDeductionsService employeeDeductionsService;
    @GetMapping("/payslips")
    public BaseApi<?> getListPayroll() {
        List<Payslip> payslips = payslipService.getListPaySlip();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payslips have been found")
                .timestamp(LocalDateTime.now())
                .data(payslips)
                .build();
    }

    @GetMapping("/payslips/report")
    public BaseApi<?> getListPayrollReport() {
        List<PayrollAndPaySlip> payslips = payslipReportService.getListPaySlip();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payslips have been found")
                .timestamp(LocalDateTime.now())
                .data(payslips)
                .build();
    }

    @GetMapping("/payslips/reportByEmId")
    public BaseApi<?> getListPayrollReportByEmId(@RequestParam Long emId) {
        List<PayrollAndPaySlip> payslips = payslipReportService.getListPayslipForEmp(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payslips have been found")
                .timestamp(LocalDateTime.now())
                .data(payslips)
                .build();
    }

    @GetMapping("/payslips/EmId")
    public BaseApi<?> getListPayrollByEmId(@RequestParam Long emId) {
        List<Payslip> payslips = payslipService.getListPaySlipByEmId(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payslips have been found")
                .timestamp(LocalDateTime.now())
                .data(payslips)
                .build();
    }

    @GetMapping("/payslips/getPayrollById")
    public BaseApi<?> getListPayrollId(@RequestParam Long id) {
        Payslip payslips = payslipService.getPaySlipById(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payslips have been found")
                .timestamp(LocalDateTime.now())
                .data(payslips)
                .build();
    }

    @DeleteMapping("/payslips/delete")
    public BaseApi<?> deletePayroll(@RequestParam Long id) {
        payslipService.delete(id);
        employeeAllowancesService.deleteEmpAllowanceByPaySlipId(id);
        employeeDeductionsService.deleteEmployeeDeductions(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payslips have been found")
                .timestamp(LocalDateTime.now())
                .data("payslips")
                .build();
    }


    @PutMapping("/payslips/update")
    public BaseApi<?> updatePaySlip(@RequestPart("body") PayslipReqDto payslipReqDto, @RequestPart("id") Long id) {
        Payslip payslips = payslipService.update(payslipReqDto,id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payslips have been update")
                .timestamp(LocalDateTime.now())
                .data(payslips)
                .build();
    }


    @PostMapping("/payslips/add")
    public BaseApi<?> addPayslip(@RequestBody PayslipReqDto payslipReqDto) {
        List<Payslip> payslips = payslipService.create(payslipReqDto);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payslips have been created")
                .timestamp(LocalDateTime.now())
                .data(payslips)
                .build();
    }
}
