package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.model.Payslip;
import com.cats.payrollservice.service.EmployeeAllowancesService;
import com.cats.payrollservice.service.EmployeeDeductionsService;
import com.cats.payrollservice.service.PayrollService;
import com.cats.payrollservice.service.PayslipService;
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


    @GetMapping("/payslips/update")
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
    public BaseApi<?> addPayslip(@RequestPart("body") PayslipReqDto payslipReqDto, @RequestPart("emId")List<Long> emId) {
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
