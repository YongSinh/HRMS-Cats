package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto;
import com.cats.payrollservice.dto.request.EmployeeDeductionsDto5;
import com.cats.payrollservice.dto.request.EmployeeDeductionsReqDto;
import com.cats.payrollservice.dto.request.EmployeeDeductionsReqDto2;
import com.cats.payrollservice.dto.response.EmployeeDeductionsRepDto;
import com.cats.payrollservice.model.EmployeeAllowances;
import com.cats.payrollservice.model.EmployeeDeductions;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EmployeeDeductionsService {

    List<EmployeeDeductions> createMultiple(EmployeeDeductionsReqDto employeeDeductionsReqDto, List<Long> emIds);
    EmployeeDeductions addDeductions(EmployeeDeductionsReqDto2 employeeDeductionsReqDto);
    List<EmployeeDeductions> addMoreToPaySlip(EmployeeDeductionsReqDto employeeDeductionsReqDto, Long emId, Long id);
    EmployeeDeductions update(EmployeeDeductionsReqDto2 employeeDeductionsReqDto, Long Id);
    EmployeeDeductions getEmployeeDeductionsById(Long id);
    List<EmployeeDeductions> getListEmployeeDeductions();
    List<EmployeeDeductions> getListEmployeeDeductionsByEmId(Long emId);
    List<EmployeeDeductionsRepDto> getListEmployeeDeductionsByPaySlipId(Long id);
    void deleteEmployeeDeductions(Long id);
    void deleteEmpDeductionsByPlaySlip(Long id);
    List<EmployeeDeductions> getDeductionsForCurrentMonth(Long emId);
    List<EmployeeDeductions> createEmployeeDeductions(List<EmployeeDeductionsDto5> deductionsDtos);
}
