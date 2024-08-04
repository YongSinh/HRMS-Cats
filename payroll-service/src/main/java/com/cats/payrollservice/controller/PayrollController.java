package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.dto.request.PayrollReqDto;
import com.cats.payrollservice.model.Allowances;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.non_entity_POJO.PayrollAndPaySlip;
import com.cats.payrollservice.service.PayrollService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;
    @GetMapping("/payroll")
    public BaseApi<?> getListPayrollById() {
        List<Payroll> payrollList = payrollService.getListPayroll();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payroll have been found")
                .timestamp(LocalDateTime.now())
                .data(payrollList)
                .build();
    }


    @GetMapping("/user/getPayrollByRefNo")
    public BaseApi<?> getPayrollByRefNo(@RequestParam String refNo) {
        PayrollAndPaySlip payrollAndPaySlip = payrollService.getPayrollByRefNo2(refNo);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payroll have been found")
                .timestamp(LocalDateTime.now())
                .data(payrollAndPaySlip)
                .build();
    }

    @GetMapping("/getPayrollByCreateDate")
    public BaseApi<?> getPayrollByCreateDate(@RequestParam String date) {
        List<PayrollAndPaySlip> payrollAndPaySlip = payrollService.getPayrollByCreateDate(date);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payroll have been found")
                .timestamp(LocalDateTime.now())
                .data(payrollAndPaySlip)
                .build();
    }


    @GetMapping("/payrollByEmId")
    public BaseApi<?> getListPayrollByEmId(@RequestParam Long emId) {
        List<Payroll> payrollList = payrollService.getListPayRollByEmId(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payroll have been found")
                .timestamp(LocalDateTime.now())
                .data(payrollList)
                .build();
    }

    @DeleteMapping("/deletePayroll")
    public BaseApi<?> deletePayroll(@RequestParam Long id) {
        Payroll payroll = payrollService.getPayrollById(id);
        payrollService.deletePayroll(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payroll have been delete`")
                .timestamp(LocalDateTime.now())
                .data(payroll)
                .build();
    }


    @GetMapping("/payrollByEmIdAndCreateDate")
    public BaseApi<?> getListPayrollByEmIdAndCreateDate(@RequestParam Long emId, @RequestParam LocalDate date) {
        Payroll payrollList = payrollService.getPayRollByEmIdAndCreateDate(emId, date);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payroll have been found")
                .timestamp(LocalDateTime.now())
                .data(payrollList)
                .build();
    }

    @GetMapping("/fetchEmployeeIds")
    public BaseApi<?> fetchEmployeeIds(@RequestParam Long depId) {
        List<Payroll> payrollList =  payrollService.findPayRollByDepEmId(depId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payroll have been found")
                .timestamp(LocalDateTime.now())
                .data(payrollList)
                .build();
    }
    @PostMapping("/addPayroll")
    public BaseApi<?> addPayroll(@RequestPart("body") PayrollReqDto payrollReqDto,@RequestPart("emIds")List<Long> emIds ) {
        List<Payroll> payrollList =  payrollService.create(payrollReqDto, emIds);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payroll have been found")
                .timestamp(LocalDateTime.now())
                .data(payrollList)
                .build();
    }
    @CircuitBreaker(name = "management", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "management")
    @Retry(name = "management")
    @PostMapping("/addPayrollAll")
    public CompletableFuture<BaseApi<?>> addPayrollAll(@RequestPart("body") PayrollReqDto payrollReqDto)  {
        return CompletableFuture.supplyAsync(() -> {
            List<Payroll> payrollList;
            try {
                 payrollList =  payrollService.createAllEmp(payrollReqDto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return BaseApi.builder()
                    .status(true)
                    .code(HttpStatus.OK.value())
                    .message("List All the Attendance")
                    .timestamp(LocalDateTime.now())
                    .data(payrollList)
                    .build();
        });
    }

    public CompletableFuture<BaseApi<Object>> fallbackMethod(PayrollReqDto payrollReqDto, Throwable throwable) {
        return CompletableFuture.supplyAsync(() -> BaseApi.builder()
                .status(false)
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .message("Service is currently unavailable. Please try again later.")
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PutMapping("/updatePayroll")
    public BaseApi<?> updatePayroll(@RequestPart("body") PayrollReqDto payrollReqDto,@RequestPart("id")Long id ) {
        Payroll payrollList =  payrollService.update(id, payrollReqDto);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("payroll have been update")
                .timestamp(LocalDateTime.now())
                .data(payrollList)
                .build();
    }


}
