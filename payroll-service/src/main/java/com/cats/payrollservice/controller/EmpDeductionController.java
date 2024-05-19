package com.cats.payrollservice.controller;

import com.cats.payrollservice.service.EmployeeDeductionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
public class EmpDeductionController {
    private final EmployeeDeductionsService employeeDeductionsService;
}
