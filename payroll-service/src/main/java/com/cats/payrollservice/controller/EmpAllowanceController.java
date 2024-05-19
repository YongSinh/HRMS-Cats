package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto;
import com.cats.payrollservice.dto.request.EmployeeDeductionsReqDto;
import com.cats.payrollservice.dto.response.EmployeeAllowancesRepDto;
import com.cats.payrollservice.model.Allowances;
import com.cats.payrollservice.model.EmployeeAllowances;
import com.cats.payrollservice.service.EmployeeAllowancesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
public class EmpAllowanceController {
    private final EmployeeAllowancesService employeeAllowancesService;
    @GetMapping("/empAllowances/getById")
    public BaseApi<?> getEmpAllowancesById(@RequestParam Long id) {
        EmployeeAllowancesRepDto employeeAllowances = employeeAllowancesService.getEmpAllowances(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Allowances have been found")
                .timestamp(LocalDateTime.now())
                .data(employeeAllowances)
                .build();
    }

    @GetMapping("/empAllowances/ByEmId")
    public BaseApi<?> getEmpAllowancesByEmId(@RequestParam Long emId) {
        List<EmployeeAllowancesRepDto> employeeAllowances = employeeAllowancesService.getListEmpAllowancesByEmId(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Allowances have been found")
                .timestamp(LocalDateTime.now())
                .data(employeeAllowances)
                .build();
    }

    @GetMapping("/empAllowances")
    public BaseApi<?> getEmpAllowances() {
        List<EmployeeAllowancesRepDto> employeeAllowances = employeeAllowancesService.getListEmpAllowances();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Allowances have been found")
                .timestamp(LocalDateTime.now())
                .data(employeeAllowances)
                .build();
    }

    @PostMapping("/empAllowances/add")
    public BaseApi<?> addEmpAllowances(@RequestBody EmployeeAllowancesReqDto employeeAllowancesReqDto) {
        EmployeeAllowancesRepDto employeeAllowances = employeeAllowancesService.create(employeeAllowancesReqDto);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Allowances have been created")
                .timestamp(LocalDateTime.now())
                .data(employeeAllowances)
                .build();
    }
    @PostMapping(value = "/empAllowances/addMultiple", consumes = {"multipart/form-data", "multipart/mixed"})
    public BaseApi<?> createMultiple(@RequestPart(name = "body") EmployeeAllowancesReqDto employeeAllowancesReqDto,
                                     @RequestPart(name = "emId") List<Long> emId) {
        List<EmployeeAllowancesRepDto> employeeAllowances = employeeAllowancesService.createMultiple(employeeAllowancesReqDto, emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Allowances have been created")
                .timestamp(LocalDateTime.now())
                .data(employeeAllowances)
                .build();
    }


    @PutMapping("/empAllowances/update")
    public BaseApi<?> updateEmpAllowances(@RequestBody EmployeeAllowancesReqDto employeeAllowancesReqDto, @RequestParam Long id) {
        EmployeeAllowancesRepDto employeeAllowances = employeeAllowancesService.update(employeeAllowancesReqDto,id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Allowances have been updated")
                .timestamp(LocalDateTime.now())
                .data(employeeAllowances)
                .build();
    }

    @DeleteMapping("/empAllowances/delete")
    public BaseApi<?> deleteEmpAllowancesById(@RequestParam Long id) {
        EmployeeAllowancesRepDto employeeAllowances = employeeAllowancesService.getEmpAllowances(id);
        employeeAllowancesService.delete(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Allowances have been found")
                .timestamp(LocalDateTime.now())
                .data(employeeAllowances)
                .build();
    }

}
