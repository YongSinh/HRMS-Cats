package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.model.EmployeeAllowances;
import com.cats.payrollservice.model.EmployeeDeductions;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.non_entity_POJO.PayrollAndPaySlip;
import com.cats.payrollservice.repository.PayrollAndPayRepo;
import com.cats.payrollservice.service.*;
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
    private final ServiceCalculate serviceCalculate;
    private final PayrollService payrollService;
    private final EmployeeAllowancesService employeeAllowancesService;
    private final EmployeeDeductionsService employeeDeductionsService;

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
    @GetMapping("/test4")
    public BaseApi<?> hello3(@RequestParam Long emId)  {
        List<EmployeeAllowances> allowances = employeeAllowancesService.getAllowancesForCurrentMonth(emId);
        List<EmployeeDeductions> deductions = employeeDeductionsService.getDeductionsForCurrentMonth(emId);
        double totalAllowances = allowances.stream().mapToDouble(EmployeeAllowances::getAmount).sum();
        double totalDeductions = deductions.stream().mapToDouble(EmployeeDeductions::getAmount).sum();
        // Base salary
        double salary = 500D;
        // Apply 10% tax deduction
        double tax = salary * 0.10;
        double salaryAfterTax = salary - tax;
        // Calculate net salary
        double net = salaryAfterTax / 2 + totalAllowances - 10;
        net = serviceCalculate.roundUp(net);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("test payroll!")
                .timestamp(LocalDateTime.now())
                .data(net)
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
