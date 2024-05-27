package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.mapper;
import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto;
import com.cats.payrollservice.dto.response.EmployeeAllowancesRepDto;
import com.cats.payrollservice.model.Allowances;
import com.cats.payrollservice.model.EmployeeAllowances;
import com.cats.payrollservice.model.Payslip;
import com.cats.payrollservice.repository.EmployeeAllowancesRepo;
import com.cats.payrollservice.repository.PayslipRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeAllowancesServiceImp implements EmployeeAllowancesService {
    private final EmployeeAllowancesRepo employeeAllowancesRepo;
    private final AllowancesService allowancesService;
    private final PayrollService payrollService;
    private final  PayslipService payslipService;
    private final PayslipRepo payslipRepo;
    @Override
    public EmployeeAllowancesRepDto create(EmployeeAllowancesReqDto employeeAllowancesReqDto) {
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
        return mapper.employeeAllowancesToEmployeeAllowancesResponseDto(employeeAllowances);
    }

    @Override
    public List<EmployeeAllowancesRepDto> createMultiple(EmployeeAllowancesReqDto employeeAllowancesReqDto, List<Long> emId, LocalDateTime localDateTime) {
        List<EmployeeAllowances> employeeAllowancesArrayList = new ArrayList<>();
        List<Payslip> payslips =  new ArrayList<>();
        for (Long emIds : emId){
            EmployeeAllowances employeeAllowances = new EmployeeAllowances();
            employeeAllowances.setEmpId(emIds);
            employeeAllowances.setType(employeeAllowancesReqDto.getType());
            employeeAllowances.setAmount(employeeAllowancesReqDto.getAmount());
            employeeAllowances.setEffectiveDate(employeeAllowancesReqDto.getEffectiveDate());
            employeeAllowances.setDateCreated(employeeAllowancesReqDto.getDateCreated());
            if (employeeAllowancesReqDto.getAllowances() == null) {
                throw new IllegalArgumentException("Allowances at least on Employee Allowances");
            }
            Allowances allowances = allowancesService.getAllowancesBytId(employeeAllowancesReqDto.getAllowances());
            employeeAllowances.setAllowances(allowances);
            employeeAllowancesArrayList.add(employeeAllowances);
            Payslip payslip = payslipService.getListPaySlipByeEmIdAndCreateDate(emIds, localDateTime);
            payslip.setAllowances(employeeAllowances.toString());
            payslip.setAllowanceAmount(employeeAllowancesReqDto.getAmount());
            payslips.add(payslip);
        }
        payslipRepo.saveAll(payslips);
        return mapper.employeeAllowancesToEmployeeAllowancesResponseDtos(employeeAllowancesRepo.saveAll(employeeAllowancesArrayList));
    }

    @Override
    public void delete(Long id) {
        EmployeeAllowances employeeAllowances = getEmpAllowancesById(id);
        employeeAllowancesRepo.delete(employeeAllowances);
    }

    @Override
    public EmployeeAllowancesRepDto update(EmployeeAllowancesReqDto employeeAllowancesReqDto, Long Id) {
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
        return mapper.employeeAllowancesToEmployeeAllowancesResponseDto(employeeAllowances);
    }

    @Override
    public EmployeeAllowances getEmpAllowancesById(Long id) {
        return employeeAllowancesRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        "Employee Allowances with id: " + id + " could not be found"));
    }

    @Override
    public EmployeeAllowancesRepDto getEmpAllowances(Long id) {
        return mapper.employeeAllowancesToEmployeeAllowancesResponseDto(getEmpAllowancesById(id));
    }

    @Override
    public List<EmployeeAllowancesRepDto> getListEmpAllowances() {

        return mapper.employeeAllowancesToEmployeeAllowancesResponseDtos(employeeAllowancesRepo.findAll());
    }

    @Override
    public List<EmployeeAllowancesRepDto> getListEmpAllowancesByEmId(Long emId) {
        return mapper.employeeAllowancesToEmployeeAllowancesResponseDtos(employeeAllowancesRepo.findByEmpId(emId));
    }
}
