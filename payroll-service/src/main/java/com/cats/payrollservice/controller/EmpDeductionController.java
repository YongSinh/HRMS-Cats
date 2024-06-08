package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto;
import com.cats.payrollservice.dto.request.EmployeeDeductionsReqDto;
import com.cats.payrollservice.dto.response.EmployeeAllowancesRepDto;
import com.cats.payrollservice.model.EmployeeDeductions;
import com.cats.payrollservice.service.EmployeeDeductionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
public class EmpDeductionController {
    private final EmployeeDeductionsService employeeDeductionsService;

    @GetMapping("/empDeduction/ByEmId")
    public BaseApi<?> getEmpDeductionByEmId(@RequestParam Long emId) {
        List<EmployeeDeductions> employeeDeductions = employeeDeductionsService.getListEmployeeDeductionsByEmId(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Deduction have been found")
                .timestamp(LocalDateTime.now())
                .data(employeeDeductions)
                .build();
    }
    @GetMapping("/getEmpDeduction")
    public BaseApi<?> getEmpDeduction() {
        List<EmployeeDeductions> employeeDeductions = employeeDeductionsService.getListEmployeeDeductions();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Deduction have been found")
                .timestamp(LocalDateTime.now())
                .data(employeeDeductions)
                .build();
    }


    @GetMapping("/empDeduction/ByPaySlip")
    public BaseApi<?> getEmpDeductionByPaySlip(@RequestParam Long id) {
        List<EmployeeDeductions> employeeDeductions = employeeDeductionsService.getListEmployeeDeductionsByPaySlipId(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Deduction have been found")
                .timestamp(LocalDateTime.now())
                .data(employeeDeductions)
                .build();
    }
    @PostMapping("/empDeduction/addDeduction")
    public BaseApi<?> createEmpDeduction(
            @RequestPart(name = "body")EmployeeDeductionsReqDto employeeDeductionsReqDto,
            @RequestPart(name = "emId") List<Long> emId
    ) {
        List<EmployeeDeductions> employeeDeductions = employeeDeductionsService.createMultiple(employeeDeductionsReqDto, emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Deduction have been create")
                .timestamp(LocalDateTime.now())
                .data(employeeDeductions)
                .build();
    }

    @PostMapping("/empDeduction/addDeductionMore")
    public BaseApi<?> createEmpDeductionMore(
            @RequestPart(name = "body")EmployeeDeductionsReqDto employeeDeductionsReqDto,
            @RequestPart(name = "emId") Long emId,
            @RequestPart(name = "id") Long id
    ) {
        List<EmployeeDeductions> employeeDeductions = employeeDeductionsService.addMoreToPaySlip(employeeDeductionsReqDto, emId, id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Deduction have been create")
                .timestamp(LocalDateTime.now())
                .data(employeeDeductions)
                .build();
    }

    @PutMapping("/empDeduction/updateDeduction")
    public BaseApi<?> updateEmpDeduction(
            @RequestPart(name = "body")EmployeeDeductionsReqDto employeeDeductionsReqDto,
            @RequestPart(name = "id") Long id
    ) {
        EmployeeDeductions employeeDeductions = employeeDeductionsService.update(employeeDeductionsReqDto, id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Deduction have been updated")
                .timestamp(LocalDateTime.now())
                .data(employeeDeductions)
                .build();
    }


}
