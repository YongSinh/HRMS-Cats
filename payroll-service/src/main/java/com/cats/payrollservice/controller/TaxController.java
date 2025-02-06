package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.dto.request.TaxReqDto;
import com.cats.payrollservice.model.Tax;
import com.cats.payrollservice.service.TaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payrolls/tax")
@RequiredArgsConstructor
public class TaxController {

    private final TaxService taxService;

    @PostMapping("/addTax")
    public BaseApi<?> addTax(@RequestBody TaxReqDto tax) {
        Tax tax1 = taxService.addTax(tax);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Tax have been added")
                .timestamp(LocalDateTime.now())
                .data(tax1)
                .build();
    }

    @PutMapping("/editTax/{id}")
    public BaseApi<?> editTax(@RequestBody TaxReqDto tax, @PathVariable Long id) {
        Tax tax1 = taxService.updateTax(tax, id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Tax have been updated")
                .timestamp(LocalDateTime.now())
                .data(tax1)
                .build();
    }

    @GetMapping("/listTax")
    public BaseApi<?> getListTax() {
        List<Tax> tax1 = taxService.getListTax();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Tax have been found")
                .timestamp(LocalDateTime.now())
                .data(tax1)
                .build();
    }

    @GetMapping("/getTaxById/{id}")
    public BaseApi<?> getTaxById(@PathVariable Long id) {
        Tax tax1 = taxService.getTaxById(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Tax have been found")
                .timestamp(LocalDateTime.now())
                .data(tax1)
                .build();
    }

    @GetMapping("/getTaxRateBySalary")
    public BaseApi<?> getTaxRateBySalary(@RequestParam Double salary) {
        Tax tax1 = taxService.getTaxRateBySalary(salary);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Tax have been found")
                .timestamp(LocalDateTime.now())
                .data(tax1)
                .build();
    }

    @GetMapping("/taxCalculator")
    public BaseApi<?> taxCalculator(@RequestParam Double salary) {
        Double tax1 = taxService.taxCalculator(salary);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Tax have been found")
                .timestamp(LocalDateTime.now())
                .data(tax1)
                .build();
    }

    @DeleteMapping("/deleteTax/{id}")
    public BaseApi<?> deleteTax(@PathVariable Long id) {
        taxService.deleteTax(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Tax have been delete")
                .timestamp(LocalDateTime.now())
                .data("Tax have been delete")
                .build();
    }
}
