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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        //employeeAllowances.setEmpId(employeeAllowancesReqDto.getEmpId());
        employeeAllowances.setType(employeeAllowancesReqDto.getType());
        employeeAllowances.setAmount(employeeAllowancesReqDto.getAmount());
        employeeAllowances.setEffectiveDate(employeeAllowancesReqDto.getEffectiveDate());
        employeeAllowances.setDateCreated(employeeAllowancesReqDto.getDateCreated());
        if (employeeAllowancesReqDto.getAllowances() == null) {
            throw new IllegalArgumentException("Allowances at least on Employee Allowances");
        }
        //Allowances allowances = allowancesService.getAllowancesBytId(employeeAllowancesReqDto.getAllowances());
       // employeeAllowances.setAllowances(allowances);
        employeeAllowancesRepo.save(employeeAllowances);
        return mapper.employeeAllowancesToEmployeeAllowancesResponseDto(employeeAllowances);
    }

    @Transactional
    @Override
    public List<EmployeeAllowancesRepDto> createMultiple(EmployeeAllowancesReqDto employeeAllowancesReqDto, List<Long> emId) {
        List<EmployeeAllowances> employeeAllowancesArrayList = new ArrayList<>();
        double totalAmount = 0.0;
        if (employeeAllowancesReqDto.getAllowances() == null || employeeAllowancesReqDto.getAllowances().isEmpty()) {
            throw new IllegalArgumentException("Allowances list cannot be null or empty.");
        }
        for (Long emIds : emId){
            List<String> allowanceList = new ArrayList<>();
            Payslip payslip = payslipService.getListPaySlipByeEmIdAndCreateDate(emIds,employeeAllowancesReqDto.getPaySlipDate());
            for (Long allowance:employeeAllowancesReqDto.getAllowances()){
                EmployeeAllowances employeeAllowances = new EmployeeAllowances();
                employeeAllowances.setEmpId(emIds);
                employeeAllowances.setType(employeeAllowancesReqDto.getType());
                employeeAllowances.setAmount(employeeAllowancesReqDto.getAmount());
                employeeAllowances.setEffectiveDate(employeeAllowancesReqDto.getEffectiveDate());
                employeeAllowances.setDateCreated(employeeAllowancesReqDto.getDateCreated());
                Allowances allowances = allowancesService.getAllowancesBytId(allowance);
                allowanceList.add(allowances.getAllowances());
                employeeAllowances.setAllowances(allowances);
                employeeAllowancesArrayList.add(employeeAllowances);
                employeeAllowances.setPaySlipId(payslip.getId());
            }
            System.out.println(employeeAllowancesReqDto.getAmount());
            totalAmount += employeeAllowancesReqDto.getAmount();
            payslipService.addAllowanceToPaySlip(emIds,employeeAllowancesReqDto.getPaySlipDate(), totalAmount, allowanceList);
        }

        employeeAllowancesRepo.saveAll(employeeAllowancesArrayList);
        return mapper.employeeAllowancesToEmployeeAllowancesResponseDtos(employeeAllowancesArrayList);
    }

    @Override
    public void delete(Long id) {
        EmployeeAllowances employeeAllowances = getEmpAllowancesById(id);
        employeeAllowancesRepo.delete(employeeAllowances);
    }
    @Transactional
    @Override
    public EmployeeAllowancesRepDto update(EmployeeAllowancesReqDto employeeAllowancesReqDto, Long Id) {
        EmployeeAllowances employeeAllowances = getEmpAllowancesById(Id);
        List<String> allowanceList = new ArrayList<>();
        double oldAmount = employeeAllowances.getAmount();
        double newAmount = employeeAllowancesReqDto.getAmount();
        payslipService.updateAllowanceToPaySlip(employeeAllowances.getPaySlipId(),newAmount, oldAmount,allowanceList);
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
    public List<EmployeeAllowancesRepDto> getListEmpAllowancesByPaySlip(Long id) {
        return mapper.employeeAllowancesToEmployeeAllowancesResponseDtos(employeeAllowancesRepo.findByPaySlipId(id));
    }

    @Override
    public List<EmployeeAllowancesRepDto> getListEmpAllowancesByEmId(Long emId) {
        return mapper.employeeAllowancesToEmployeeAllowancesResponseDtos(employeeAllowancesRepo.findByEmpId(emId));
    }
}
