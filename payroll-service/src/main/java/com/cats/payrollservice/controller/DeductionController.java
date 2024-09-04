package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.dto.request.AllowancesReqDto;
import com.cats.payrollservice.dto.request.DeductionsReqDto;
import com.cats.payrollservice.model.Allowances;
import com.cats.payrollservice.model.Deductions;
import com.cats.payrollservice.service.DeductionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
public class DeductionController {
    private final DeductionsService deductionsService;
    @PostMapping("/deductions/addDeduction")
    public BaseApi<?> addDeduction(@RequestBody DeductionsReqDto deductionsReqDto) {
        Deductions deductions = deductionsService.create(deductionsReqDto);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("deductions have been added")
                .timestamp(LocalDateTime.now())
                .data(deductions)
                .build();
    }

    @PutMapping("/deductions/updateDeduction")
    public BaseApi<?> updateDeduction(@RequestBody DeductionsReqDto deductionsReqDto, @RequestParam Long id) {
        Deductions deductions = deductionsService.update(deductionsReqDto, id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("deductions have been update")
                .timestamp(LocalDateTime.now())
                .data(deductions)
                .build();
    }
    @DeleteMapping("/deductions/deleteDeductionById")
    public BaseApi<?> deleteDeductionById( @RequestParam Long id) {
        deductionsService.delete(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("deductions have been delete")
                .timestamp(LocalDateTime.now())
                .data("deductions have been delete")
                .build();
    }
    @GetMapping("/deductions/getDeductionById")
    public BaseApi<?> getDeductionById( @RequestParam Long id) {
        Deductions deductions = deductionsService.getDeductionsById(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("deductions have been found")
                .timestamp(LocalDateTime.now())
                .data(deductions)
                .build();
    }

    @GetMapping("/deductions")
    public BaseApi<?> getDeduction() {
        List<Deductions> deductions = deductionsService.getListDeduction();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("deductions have been found")
                .timestamp(LocalDateTime.now())
                .data(deductions)
                .build();
    }
}
