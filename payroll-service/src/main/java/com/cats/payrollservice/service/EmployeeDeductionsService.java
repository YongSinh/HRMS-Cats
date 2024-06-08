package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto;
import com.cats.payrollservice.dto.request.EmployeeDeductionsReqDto;
import com.cats.payrollservice.model.EmployeeAllowances;
import com.cats.payrollservice.model.EmployeeDeductions;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EmployeeDeductionsService {

    List<EmployeeDeductions> createMultiple(EmployeeDeductionsReqDto employeeDeductionsReqDto, List<Long> emIds);
    List<EmployeeDeductions> addMoreToPaySlip(EmployeeDeductionsReqDto employeeDeductionsReqDto, Long emId, Long id);
    EmployeeDeductions update(EmployeeDeductionsReqDto employeeDeductionsReqDto, Long Id);
    EmployeeDeductions getEmployeeDeductionsById(Long id);
    List<EmployeeDeductions> getListEmployeeDeductions();
    List<EmployeeDeductions> getListEmployeeDeductionsByEmId(Long emId);
    List<EmployeeDeductions> getListEmployeeDeductionsByPaySlipId(Long id);

}
