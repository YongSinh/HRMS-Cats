package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.non_entity_POJO.PayrollAndPaySlip;
import com.cats.payrollservice.repository.PayrollAndPayRepo;
import com.cats.payrollservice.service.ApiService;
import com.cats.payrollservice.service.PayrollService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
public class testController {

    private final ApiService apiService;
    private final PayrollService payrollAndPayRepo;
    private final PayrollService payrollService;

    @GetMapping("/test2")
    public BaseApi<?> hello() {
        PayrollAndPaySlip payrollAndPaySlip = payrollAndPayRepo.getPayrollByRefNo2("20240627223858-287562");
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("test done!")
                .timestamp(LocalDateTime.now())
                .data(payrollAndPaySlip)
                .build();
    }

    @GetMapping("/test3")
    public BaseApi<?> hello2(@RequestParam Long emId)  {
        Payroll payroll = payrollService.getPayrollsForCurrentMonth(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("test payroll!")
                .timestamp(LocalDateTime.now())
                .data(payroll)
                .build();
    }


    @CircuitBreaker(name = "management", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "management")
    @Retry(name = "management")
    @GetMapping("/test")
    public CompletableFuture<BaseApi<Object>> getListAttendanceForManager() {
        return apiService.getListAllEmployeeOnlyEmIdTest()
                .collectList()
                .map(getList -> BaseApi.builder()
                        .status(true)
                        .code(HttpStatus.OK.value())
                        .message("List All the Attendance")
                        .timestamp(LocalDateTime.now())
                        .data(getList)
                        .build())
                .toFuture();
    }

    public CompletableFuture<BaseApi<Object>> fallbackMethod(Throwable throwable) {
        return CompletableFuture.supplyAsync(() -> BaseApi.builder()
                .status(false)
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .message("Service is currently unavailable. Please try again later.")
                .timestamp(LocalDateTime.now())
                .build());
    }
}
