package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.dto.request.AllowancesReqDto;
import com.cats.payrollservice.dto.request.TaxReqDto;
import com.cats.payrollservice.model.Allowances;
import com.cats.payrollservice.model.Tax;
import com.cats.payrollservice.service.AllowancesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
public class AllowanceController {
    private final AllowancesService allowancesService;
    @PostMapping("/allowances/addAllowances")
    public BaseApi<?> addAllowances(@RequestBody AllowancesReqDto allowancesReqDto) {
        Allowances allowances = allowancesService.create(allowancesReqDto);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("allowances have been added")
                .timestamp(LocalDateTime.now())
                .data(allowances)
                .build();
    }
    @PutMapping("/allowances/updateAllowances")
    public BaseApi<?> updateAllowances(@RequestBody AllowancesReqDto allowancesReqDto, @RequestParam Long id) {
        Allowances allowances = allowancesService.update(allowancesReqDto, id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("allowances have been update")
                .timestamp(LocalDateTime.now())
                .data(allowances)
                .build();
    }

    @GetMapping("/allowances")
    public BaseApi<?> getListAllowances() {
        List<Allowances> allowances = allowancesService.getListAllowances();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("allowances have been found")
                .timestamp(LocalDateTime.now())
                .data(allowances)
                .build();
    }

    @GetMapping("/allowancesById")
    public BaseApi<?> getListAllowancesById(@RequestParam Long id) {
        Allowances allowances = allowancesService.getAllowancesBytId(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("allowances have been found")
                .timestamp(LocalDateTime.now())
                .data(allowances)
                .build();
    }

    @DeleteMapping("/allowances/delete")
    public BaseApi<?> deleteAllowancesById(@RequestParam Long id) {
        //Allowances allowances = allowancesService.getAllowancesBytId(id);
        allowancesService.delete(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("allowances have been deleted")
                .timestamp(LocalDateTime.now())
                .data("allowances have been deleted")
                .build();
    }
}
