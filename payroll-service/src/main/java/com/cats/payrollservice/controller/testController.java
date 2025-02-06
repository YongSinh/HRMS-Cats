package com.cats.payrollservice.controller;

import com.cats.payrollservice.base.BaseApi;
import com.cats.payrollservice.dto.response.EmployeeAllowancesRepDto;
import com.cats.payrollservice.dto.response.EmployeeDeductionsRepDto;
import com.cats.payrollservice.non_entity_POJO.PaySlipReportDto;
import com.cats.payrollservice.repository.PayslipReprotRepo;
import com.cats.payrollservice.service.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
public class testController {

    private final ApiService apiService;
    private final ServiceCalculate serviceCalculate;
    private final PayrollService payrollService;
    private final EmployeeAllowancesService employeeAllowancesService;
    private final EmployeeDeductionsService employeeDeductionsService;
    private final PayslipReprotRepo payslipReprotRepo;

    @Transactional
    @GetMapping("/test3")
    public BaseApi<?> hello2(@RequestParam String ref) {
        List<PaySlipReportDto> payroll = payslipReprotRepo.getFirstAndSecondPayments(ref);
        Map<String, List<PaySlipReportDto>> groupedPayroll = payroll.stream()
                .collect(Collectors.groupingBy(PaySlipReportDto::getPayment_sequence));

        List<PaySlipReportDto> firstPayments = groupedPayroll.getOrDefault("First Payment", List.of());
        List<PaySlipReportDto> secondPayments = groupedPayroll.getOrDefault("Second Payment", List.of());

        // Handle each list
        System.out.println("First Payments: " + firstPayments.size());
        System.out.println("Second Payments: " + secondPayments.size());
        payroll.forEach(System.out::println);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("test payroll!")
                .timestamp(LocalDateTime.now())
                .data(payroll)
                .build();
    }

    @GetMapping("/test4")
    public BaseApi<?> hello3(@RequestParam Long emId) {
        List<EmployeeAllowancesRepDto> allowances = employeeAllowancesService.getAllowancesForCurrentMonth(emId);
        List<EmployeeDeductionsRepDto> deductions = employeeDeductionsService.getDeductionsForCurrentMonth(emId);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("test payroll!")
                .timestamp(LocalDateTime.now())
                .data(allowances)
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
