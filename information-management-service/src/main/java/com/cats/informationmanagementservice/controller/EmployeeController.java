package com.cats.informationmanagementservice.controller;

import com.cats.informationmanagementservice.Dto.EmployeeDtoRep;
import com.cats.informationmanagementservice.Dto.EmployeeDtoReq;
import com.cats.informationmanagementservice.Dto.FamilyDataDtoReq;
import com.cats.informationmanagementservice.base.BaseApi;
import com.cats.informationmanagementservice.model.Employee;
import com.cats.informationmanagementservice.model.FamilyData;
import com.cats.informationmanagementservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/info/employee")
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
    public BaseApi<?> addFamilyData(@RequestBody EmployeeDtoReq employeeDtoReq) {
        Employee employee = employeeService.addPersonalData(employeeDtoReq);
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

}
