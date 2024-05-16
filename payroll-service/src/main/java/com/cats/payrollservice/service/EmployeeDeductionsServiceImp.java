package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto;
import com.cats.payrollservice.model.EmployeeDeductions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeDeductionsServiceImp implements EmployeeDeductionsService{
    @Override
    public EmployeeDeductions create(EmployeeAllowancesReqDto employeeAllowancesReqDto) {
        return null;
    }

    @Override
    public EmployeeDeductions update(EmployeeAllowancesReqDto employeeAllowancesReqDto, Long Id) {
        return null;
    }

    @Override
    public EmployeeDeductions getEmployeeDeductionsById(Long id) {
        return null;
    }

    @Override
    public List<EmployeeDeductions> getListEmployeeDeductions() {
        return null;
    }

    @Override
    public List<EmployeeDeductions> getListEmployeeDeductionsByEmId(Long emId) {
        return null;
    }
}
