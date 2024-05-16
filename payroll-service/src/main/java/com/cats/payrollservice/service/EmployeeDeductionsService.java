package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto;
import com.cats.payrollservice.model.EmployeeAllowances;
import com.cats.payrollservice.model.EmployeeDeductions;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EmployeeDeductionsService {
    EmployeeDeductions create(EmployeeAllowancesReqDto employeeAllowancesReqDto);
    EmployeeDeductions update(EmployeeAllowancesReqDto employeeAllowancesReqDto, Long Id);
    EmployeeDeductions getEmployeeDeductionsById(Long id);
    List<EmployeeDeductions> getListEmployeeDeductions();
    List<EmployeeDeductions> getListEmployeeDeductionsByEmId(Long emId);
}
