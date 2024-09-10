package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.dto.request.SalariesReqDto;
import com.cats.payrollservice.dto.request.TaxReqDto;
import com.cats.payrollservice.dto.response.SalariesRepDto;
import com.cats.payrollservice.model.Salaries;
import com.cats.payrollservice.model.Tax;
import com.cats.payrollservice.service.SalariesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payrolls/salary")
@RequiredArgsConstructor
public class SalariesController {
    private final SalariesService salariesService;
    @PostMapping("/addSalary")
    public BaseApi<?> addSalary(@RequestBody SalariesReqDto salariesReqDto) {
        SalariesRepDto salaries= salariesService.addSalary(salariesReqDto);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Salaries have been added")
                .timestamp(LocalDateTime.now())
                .data(salaries)
                .build();
    }

    @PostMapping("/addSalaryList")
    public BaseApi<?> addSalaryList(
            @RequestPart("body") SalariesReqDto salariesReqDto, @RequestPart("body") List<Long> emId) {
        List<SalariesRepDto> salaries= salariesService.addSalaryList(salariesReqDto, emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Salaries have been added")
                .timestamp(LocalDateTime.now())
                .data(salaries)
                .build();
    }


    @PutMapping ("/editSalary")
    public BaseApi<?> editSalary(@RequestBody SalariesReqDto salariesReqDto, @RequestParam Long id) {
        SalariesRepDto salaries= salariesService.editSalary(salariesReqDto, id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Salaries have been updated")
                .timestamp(LocalDateTime.now())
                .data(salaries)
                .build();
    }
    @GetMapping ("/getSalaryById")
    public BaseApi<?> getSalaryById( @RequestParam Long id) {
        SalariesRepDto salaries= salariesService.getSalaryById( id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Salaries have been found")
                .timestamp(LocalDateTime.now())
                .data(salaries)
                .build();
    }

    @DeleteMapping ("/deleteSalaryById")
    public BaseApi<?> deleteSalaryById( @RequestParam Long id) {
        SalariesRepDto salaries= salariesService.getSalaryById( id);
        salariesService.deleteSalary(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Salaries have been deleted")
                .timestamp(LocalDateTime.now())
                .data(salaries)
                .build();
    }


    @GetMapping ("/getSalaryByEmId")
    public BaseApi<?> getSalaryByEmId( @RequestParam Long emId) {
        SalariesRepDto salaries= salariesService.getSalaryByEmId( emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Salaries have been found")
                .timestamp(LocalDateTime.now())
                .data(salaries)
                .build();
    }

    @GetMapping ("/getSalaryByDepId")
    public BaseApi<?> getSalaryByDepId( @RequestParam(name = "depId") Long depId) {
        List<SalariesRepDto> salaries= salariesService.getListSalaryDepId( depId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Salaries have been found")
                .timestamp(LocalDateTime.now())
                .data(salaries)
                .build();
    }

    @GetMapping ("/getListSalary")
    public BaseApi<?> getListSalary() {
        List<SalariesRepDto> salaries= salariesService.getListSalary();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Salaries have been found")
                .timestamp(LocalDateTime.now())
                .data(salaries)
                .build();
    }
}
