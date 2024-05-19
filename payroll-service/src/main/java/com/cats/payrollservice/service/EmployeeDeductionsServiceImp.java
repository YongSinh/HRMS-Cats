package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto;
import com.cats.payrollservice.dto.request.EmployeeDeductionsReqDto;
import com.cats.payrollservice.model.Allowances;
import com.cats.payrollservice.model.Deductions;
import com.cats.payrollservice.model.EmployeeAllowances;
import com.cats.payrollservice.model.EmployeeDeductions;
import com.cats.payrollservice.repository.EmployeeDeductionsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeDeductionsServiceImp implements EmployeeDeductionsService{
    private final EmployeeDeductionsRepo employeeDeductionsRepo;
    private final DeductionsService deductionsService;
    @Override
    public EmployeeDeductions create(EmployeeDeductionsReqDto employeeDeductionsReqDto) {
        EmployeeDeductions employeeDeductions = new EmployeeDeductions();
        employeeDeductions.setEmpId(employeeDeductionsReqDto.getEmpId());
        employeeDeductions.setType(employeeDeductionsReqDto.getType());
        employeeDeductions.setAmount(employeeDeductionsReqDto.getAmount());
        employeeDeductions.setEffectiveDate(employeeDeductionsReqDto.getEffectiveDate());
        employeeDeductions.setDateCreated(employeeDeductionsReqDto.getDateCreated());
        if (employeeDeductionsReqDto.getDeductions() == null) {
            throw new IllegalArgumentException("Deductions at least on Employee Allowances");
        }
        Deductions deductions = deductionsService.getDeductionsById(employeeDeductionsReqDto.getDeductions());
        employeeDeductions.setDeductions(deductions);
        return employeeDeductionsRepo.save(employeeDeductions);
    }

    @Override
    public List<EmployeeDeductions> createMultiple(EmployeeDeductionsReqDto employeeDeductionsReqDto, List<Long> emIds) {
        List<EmployeeDeductions> employeeDeductionsList = new ArrayList<>();
        for (Long emId : emIds){
            EmployeeDeductions employeeDeductions = new EmployeeDeductions();
            employeeDeductions.setEmpId(emId);
            employeeDeductions.setType(employeeDeductionsReqDto.getType());
            employeeDeductions.setAmount(employeeDeductionsReqDto.getAmount());
            employeeDeductions.setEffectiveDate(employeeDeductionsReqDto.getEffectiveDate());
            employeeDeductions.setDateCreated(employeeDeductionsReqDto.getDateCreated());
            if (employeeDeductionsReqDto.getDeductions() == null) {
                throw new IllegalArgumentException("Deductions at least on Employee Allowances");
            }
            Deductions deductions = deductionsService.getDeductionsById(employeeDeductionsReqDto.getDeductions());
            employeeDeductions.setDeductions(deductions);

        }
        return employeeDeductionsRepo.saveAll(employeeDeductionsList);
    }

    @Override
    public EmployeeDeductions update(EmployeeDeductionsReqDto employeeDeductionsReqDto, Long Id) {
        EmployeeDeductions employeeDeductions = getEmployeeDeductionsById(Id);
        employeeDeductions.setEmpId(employeeDeductionsReqDto.getEmpId());
        employeeDeductions.setType(employeeDeductionsReqDto.getType());
        employeeDeductions.setAmount(employeeDeductionsReqDto.getAmount());
        employeeDeductions.setEffectiveDate(employeeDeductionsReqDto.getEffectiveDate());
        employeeDeductions.setDateCreated(employeeDeductionsReqDto.getDateCreated());
        if (employeeDeductionsReqDto.getDeductions() != null) {
            Deductions deductions = deductionsService.getDeductionsById(employeeDeductionsReqDto.getDeductions());
            employeeDeductions.setDeductions(deductions);
        }

        return employeeDeductionsRepo.save(employeeDeductions);
    }

    @Override
    public EmployeeDeductions getEmployeeDeductionsById(Long id) {
        return employeeDeductionsRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        "Employee Deductions with id: " + id + " could not be found"));
    }

    @Override
    public List<EmployeeDeductions> getListEmployeeDeductions() {
        return employeeDeductionsRepo.findAll();
    }

    @Override
    public List<EmployeeDeductions> getListEmployeeDeductionsByEmId(Long emId) {
        return employeeDeductionsRepo.findByEmpId(emId);
    }
}
