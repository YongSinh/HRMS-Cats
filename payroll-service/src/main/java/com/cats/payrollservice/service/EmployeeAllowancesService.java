package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto;
import com.cats.payrollservice.dto.response.EmployeeAllowancesRepDto;
import com.cats.payrollservice.model.EmployeeAllowances;

import java.time.LocalDateTime;
import java.util.List;


public interface EmployeeAllowancesService {
    EmployeeAllowancesRepDto create(EmployeeAllowancesReqDto employeeAllowancesReqDto);
    List<EmployeeAllowancesRepDto> createMultiple(EmployeeAllowancesReqDto employeeAllowancesReqDto, List<Long> emId, LocalDateTime localDateTime);
    void delete(Long id);
    EmployeeAllowancesRepDto update(EmployeeAllowancesReqDto employeeAllowancesReqDto, Long Id);
    EmployeeAllowances getEmpAllowancesById(Long id);
    EmployeeAllowancesRepDto getEmpAllowances(Long id);
    List<EmployeeAllowancesRepDto> getListEmpAllowances();
    List<EmployeeAllowancesRepDto> getListEmpAllowancesByEmId(Long emId);
}
