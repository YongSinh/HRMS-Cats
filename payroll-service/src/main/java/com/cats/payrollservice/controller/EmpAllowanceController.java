package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.dto.request.EmployeeAllowancesDto5;
import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto;
import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto2;
import com.cats.payrollservice.dto.response.EmployeeAllowancesRepDto;
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

    @GetMapping("/getAllowancesForCurrentMonth")
    public BaseApi<?> getAllowancesForCurrentMonth(@RequestParam Long emId) {
        List<EmployeeAllowancesRepDto> employeeAllowances = employeeAllowancesService.getAllowancesForCurrentMonth(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Allowances have been found")
                .timestamp(LocalDateTime.now())
                .data(employeeAllowances)
                .build();
    }

    @GetMapping("/empAllowances/ByPaySlipId")
    public BaseApi<?> getListEmpAllowancesByPaySlip(@RequestParam Long id) {
        List<EmployeeAllowancesRepDto> employeeAllowances = employeeAllowancesService.getListEmpAllowancesByPaySlip(id);
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

    @PostMapping(value = "/empAllowances/add")
    public BaseApi<?> create(@RequestBody EmployeeAllowancesReqDto2 employeeAllowancesReqDto) {
        EmployeeAllowancesRepDto employeeAllowances = employeeAllowancesService.addAllowances(employeeAllowancesReqDto);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Allowances have been created")
                .timestamp(LocalDateTime.now())
                .data(employeeAllowances)
                .build();
    }

    @PostMapping(value = "/v2/empAllowances/add")
    public BaseApi<?> createEmployeeAllowances(@RequestBody List<EmployeeAllowancesDto5> employeeAllowancesReqDto) {
        List<EmployeeAllowances> employeeAllowances = employeeAllowancesService.createEmployeeAllowances(employeeAllowancesReqDto);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Allowances have been created")
                .timestamp(LocalDateTime.now())
                .data(employeeAllowances)
                .build();
    }

    @PutMapping("/empAllowances/addMore")
    public BaseApi<?> addMoreEmpAllowances(@RequestPart(name = "body") EmployeeAllowancesReqDto employeeAllowancesReqDto, @RequestPart(name = "emId") Long emId, @RequestParam(name = "id") Long id) {
        List<EmployeeAllowancesRepDto> employeeAllowances = employeeAllowancesService.addMoreToPaySlip(employeeAllowancesReqDto, emId, id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Allowances have been updated")
                .timestamp(LocalDateTime.now())
                .data(employeeAllowances)
                .build();
    }

    @PutMapping("/empAllowances/update")
    public BaseApi<?> updateEmpAllowances(@RequestBody EmployeeAllowancesReqDto2 employeeAllowancesReqDto, @RequestParam(name = "id") Long id) {
        EmployeeAllowancesRepDto employeeAllowances = employeeAllowancesService.update(employeeAllowancesReqDto, id);
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
