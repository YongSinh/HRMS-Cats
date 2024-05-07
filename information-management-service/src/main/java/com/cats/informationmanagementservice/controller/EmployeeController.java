package com.cats.informationmanagementservice.controller;

import com.cats.informationmanagementservice.Dto.EmployeeDtoRep;
import com.cats.informationmanagementservice.Dto.EmployeeDtoReq;
import com.cats.informationmanagementservice.Dto.FamilyDataDtoReq;
import com.cats.informationmanagementservice.base.BaseApi;
import com.cats.informationmanagementservice.model.Employee;
import com.cats.informationmanagementservice.model.FamilyData;
import com.cats.informationmanagementservice.service.EmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/info/employee")
@Slf4j
public class EmployeeController {
    private final EmployeeService employeeService;
    //@ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/hello")
    public BaseApi<?> findAllAccountTypes() {

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account types have been found")
                .timestamp(LocalDateTime.now())
                .data("Hello world")
                .build();
    }
    @PostMapping("/addEmployee")
    public BaseApi<?> addFamilyData(@RequestPart("body") EmployeeDtoReq employeeDtoReq, @RequestPart("file") MultipartFile file) throws IOException {
        Employee employee = employeeService.addPersonalData(employeeDtoReq, file);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Family data have been created")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }
    @GetMapping("/listEmployee")
    public BaseApi<?> listEmployee() {
        List<EmployeeDtoRep> employee = employeeService.listEmployee();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Family data have been created")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }
    @GetMapping("/getEmployeeById/{Id}")
    public BaseApi<?> getEmployeeById(@PathVariable Long Id) {
        EmployeeDtoRep employee = employeeService.getEmployeeDtoRepById(Id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Family data have been created")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }
    @PutMapping("/editEmployee/{Id}")
    public BaseApi<?> editEmployee(@RequestBody EmployeeDtoReq employeeDtoReq, @PathVariable Long Id) {
        EmployeeDtoRep employee = employeeService.editPersonalData(employeeDtoReq,Id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Family data have been created")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }
    @CircuitBreaker(name = "attendance",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "attendance")
    @Retry(name = "attendance")
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST, consumes = { "multipart/form-data"})
    public CompletableFuture<?> uploadFile(@RequestPart("file") @Valid MultipartFile file) throws IOException {

            LocalDate localDate = LocalDate.now();
            employeeService.uploadFile(file,2431L, 1, localDate);
            String message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return CompletableFuture.supplyAsync(() ->message);

    }
    public CompletableFuture<?>  fallbackMethod(MultipartFile file, RuntimeException runtimeException) {
        System.out.println("Cannot Place Order Executing Fallback logic");
        return  CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, please order after some time!");
    }
}
