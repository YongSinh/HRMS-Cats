package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto;
import com.cats.payrollservice.model.Allowances;
import com.cats.payrollservice.model.EmployeeAllowances;
import com.cats.payrollservice.repository.EmployeeAllowancesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeAllowancesServiceImp implements EmployeeAllowancesService {
    private final EmployeeAllowancesRepo employeeAllowancesRepo;
    private final AllowancesService allowancesService;
    @Override
    public EmployeeAllowances create(EmployeeAllowancesReqDto employeeAllowancesReqDto) {
        EmployeeAllowances employeeAllowances = new EmployeeAllowances();
        employeeAllowances.setEmpId(employeeAllowancesReqDto.getEmpId());
        employeeAllowances.setType(employeeAllowancesReqDto.getType());
        employeeAllowances.setAmount(employeeAllowancesReqDto.getAmount());
        employeeAllowances.setEffectiveDate(employeeAllowancesReqDto.getEffectiveDate());
        employeeAllowances.setDateCreated(employeeAllowancesReqDto.getDateCreated());
        if (employeeAllowancesReqDto.getAllowances() == null) {
            throw new IllegalArgumentException("Allowances at least on Employee Allowances");
        }
        Allowances allowances = allowancesService.getAllowancesBytId(employeeAllowancesReqDto.getAllowances());
        employeeAllowances.setAllowances(allowances);
        employeeAllowancesRepo.save(employeeAllowances);
        return employeeAllowances;
    }

    @Override
    public EmployeeAllowances update(EmployeeAllowancesReqDto employeeAllowancesReqDto, Long Id) {
        EmployeeAllowances employeeAllowances = getEmpAllowancesById(Id);
        employeeAllowances.setEmpId(employeeAllowancesReqDto.getEmpId());
        employeeAllowances.setType(employeeAllowancesReqDto.getType());
        employeeAllowances.setAmount(employeeAllowancesReqDto.getAmount());
        employeeAllowances.setEffectiveDate(employeeAllowancesReqDto.getEffectiveDate());
        employeeAllowances.setDateCreated(employeeAllowancesReqDto.getDateCreated());
        if (employeeAllowancesReqDto.getAllowances() != null) {
            Allowances allowances = allowancesService.getAllowancesBytId(employeeAllowancesReqDto.getAllowances());
            employeeAllowances.setAllowances(allowances);
        }
        employeeAllowancesRepo.save(employeeAllowances);
        return employeeAllowances;
    }

    @Override
    public EmployeeAllowances getEmpAllowancesById(Long id) {
        return employeeAllowancesRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        "Employee Allowances with id: " + id + " could not be found"));
    }

    @Override
    public List<EmployeeAllowances> getListEmpAllowances() {
        return employeeAllowancesRepo.findAll();
    }

    @Override
    public List<EmployeeAllowances> getListEmpAllowancesByEmId(Long emId) {
        return employeeAllowancesRepo.findByEmpId(emId);
    }
}
