package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto;
import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto2;
import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto3;
import com.cats.payrollservice.dto.response.EmployeeAllowancesRepDto;
import com.cats.payrollservice.model.EmployeeAllowances;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface EmployeeAllowancesService {
    List<EmployeeAllowancesRepDto> addAllowancesThatSame(EmployeeAllowancesReqDto3 employeeAllowancesReqDto);
    List<EmployeeAllowancesRepDto> addMoreToPaySlip(EmployeeAllowancesReqDto employeeAllowancesReqDto, Long emId, Long id);
    List<EmployeeAllowancesRepDto> createMultiple(EmployeeAllowancesReqDto employeeAllowancesReqDto, List<Long> emId);
    EmployeeAllowancesRepDto addAllowances(EmployeeAllowancesReqDto2 employeeAllowancesReqDto);
    List<EmployeeAllowances> getAllowancesForCurrentMonth(Long emId);
    void delete(Long id);
    void deleteEmpAllowanceByPaySlipId(Long id);
    EmployeeAllowancesRepDto update(EmployeeAllowancesReqDto2 employeeAllowancesReqDto, Long Id);
    EmployeeAllowances getEmpAllowancesById(Long id);
    EmployeeAllowancesRepDto getEmpAllowances(Long id);
    List<EmployeeAllowancesRepDto> getListEmpAllowances();
    List<EmployeeAllowancesRepDto> getListEmpAllowancesByPaySlip(Long id);
    List<EmployeeAllowancesRepDto> getListEmpAllowancesByEmId(Long emId);
}
