package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.service.ApiService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
public class testController {

    private final ApiService apiService;

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
