package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto;
import com.cats.payrollservice.dto.request.EmployeeDeductionsDto5;
import com.cats.payrollservice.dto.request.EmployeeDeductionsReqDto;
import com.cats.payrollservice.dto.request.EmployeeDeductionsReqDto2;
import com.cats.payrollservice.dto.response.EmployeeAllowancesRepDto;
import com.cats.payrollservice.dto.response.EmployeeDeductionsRepDto;
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

    @GetMapping("/getDeductionsForCurrentMonth")
    public BaseApi<?> getDeductionsForCurrentMonth(@RequestParam Long emId) {
        List<EmployeeDeductions> employeeDeductions = employeeDeductionsService.getDeductionsForCurrentMonth(emId);
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
        List<EmployeeDeductionsRepDto> employeeDeductions = employeeDeductionsService.getListEmployeeDeductionsByPaySlipId(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Deduction have been found")
                .timestamp(LocalDateTime.now())
                .data(employeeDeductions)
                .build();
    }
    @PostMapping("/empDeduction/addDeduction")
    public BaseApi<?> createEmpDeduction(@RequestBody EmployeeDeductionsReqDto2 employeeDeductionsReqDto) {
        EmployeeDeductions employeeDeductions = employeeDeductionsService.addDeductions(employeeDeductionsReqDto);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Deduction have been create")
                .timestamp(LocalDateTime.now())
                .data(employeeDeductions)
                .build();
    }

    @PostMapping("/v2/empDeduction/addDeduction")
    public BaseApi<?> createEmpDeductionV2(@RequestBody List<EmployeeDeductionsDto5> deductionsDtos) {
        List<EmployeeDeductions> employeeDeductions = employeeDeductionsService.createEmployeeDeductions(deductionsDtos);
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
            @RequestBody EmployeeDeductionsReqDto2 employeeDeductionsReqDto,
            @RequestParam(name = "id") Long id
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

    @DeleteMapping("/empDeduction/ByEmId")
    public BaseApi<?> deleteEmpDeductionByEmId(@RequestParam Long id) {
        EmployeeDeductions employeeDeductions = employeeDeductionsService.getEmployeeDeductionsById(id);
        employeeDeductionsService.deleteEmployeeDeductions(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Deduction have been delete")
                .timestamp(LocalDateTime.now())
                .data(employeeDeductions)
                .build();
    }

}
