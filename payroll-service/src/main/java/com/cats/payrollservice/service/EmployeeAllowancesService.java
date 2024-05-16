package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto;
import com.cats.payrollservice.model.EmployeeAllowances;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EmployeeAllowancesService {
    EmployeeAllowances create(EmployeeAllowancesReqDto employeeAllowancesReqDto);
    EmployeeAllowances update(EmployeeAllowancesReqDto employeeAllowancesReqDto, Long Id);
    EmployeeAllowances getEmpAllowancesById(Long id);
    List<EmployeeAllowances> getListEmpAllowances();
    List<EmployeeAllowances> getListEmpAllowancesByEmId(Long emId);
}
