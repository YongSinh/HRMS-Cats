package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.mapper;
import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto;
import com.cats.payrollservice.dto.request.EmployeeDeductionsReqDto;
import com.cats.payrollservice.dto.request.EmployeeDeductionsReqDto2;
import com.cats.payrollservice.dto.response.EmployeeDeductionsRepDto;
import com.cats.payrollservice.model.*;
import com.cats.payrollservice.repository.EmployeeDeductionsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableTransactionManagement
public class EmployeeDeductionsServiceImp implements EmployeeDeductionsService{
    private final EmployeeDeductionsRepo employeeDeductionsRepo;
    private final DeductionsService deductionsService;
    private final PayslipService payslipService;


    @Transactional
    @Override
    public List<EmployeeDeductions> createMultiple(EmployeeDeductionsReqDto employeeDeductionsReqDto, List<Long> emIds) {
        List<EmployeeDeductions> employeeDeductionsList = new ArrayList<>();
        double totalAmount = 0.0;
        if (employeeDeductionsReqDto.getDeductions() == null ||employeeDeductionsReqDto.getDeductions().isEmpty()) {
            throw new IllegalArgumentException("Deductions list cannot be null or empty.");
        }
        for (Long emId : emIds){
            List<String> deductionList = new ArrayList<>();
            Payslip payslip = payslipService.getListPaySlipByeEmIdAndCreateDate(emId,employeeDeductionsReqDto.getPaySlipDate());
            for(Long deduction: employeeDeductionsReqDto.getDeductions()){
                EmployeeDeductions employeeDeductions = new EmployeeDeductions();
                employeeDeductions.setEmpId(emId);
                employeeDeductions.setType(employeeDeductionsReqDto.getType());
                employeeDeductions.setAmount(employeeDeductionsReqDto.getAmount());
                employeeDeductions.setEffectiveDate(employeeDeductionsReqDto.getEffectiveDate());
                employeeDeductions.setDateCreated(employeeDeductionsReqDto.getDateCreated());
                employeeDeductions.setPaySlipId(payslip.getId());
                Deductions deductions = deductionsService.getDeductionsById(deduction);
                deductionList.add(deductions.getDeduction());
                employeeDeductions.setDeductions(deductions);
            }
            totalAmount+= employeeDeductionsReqDto.getAmount();
            payslipService.addDeductionsToPaySlip(emId,employeeDeductionsReqDto.getPaySlipDate(),totalAmount,deductionList);

        }
        return employeeDeductionsRepo.saveAll(employeeDeductionsList);
    }

    @Override
    public EmployeeDeductions addDeductions(EmployeeDeductionsReqDto2 employeeDeductionsReqDto) {
        if (employeeDeductionsReqDto.getDeductions() == null) {
            throw new IllegalArgumentException("Deductions list cannot be null or empty.");
        }
        Payslip payslip = payslipService.getPaySlipById(employeeDeductionsReqDto.getPaySlipId());
        EmployeeDeductions employeeDeductions = new EmployeeDeductions();
        employeeDeductions.setEmpId(payslip.getEmpId());
        employeeDeductions.setType(employeeDeductionsReqDto.getType());
        employeeDeductions.setAmount(employeeDeductionsReqDto.getAmount());
        employeeDeductions.setEffectiveDate(employeeDeductionsReqDto.getEffectiveDate());
        employeeDeductions.setDateCreated(employeeDeductionsReqDto.getDateCreated());
        employeeDeductions.setPaySlipId(payslip.getId());
        Deductions deductions = deductionsService.getDeductionsById(employeeDeductionsReqDto.getDeductions());
        employeeDeductions.setDeductions(deductions);
        employeeDeductionsRepo.save(employeeDeductions);
        payslipService.addDeductionsToPaySlipMore2(payslip.getId(), employeeDeductionsReqDto.getAmount(), deductions.getDeduction());
        return employeeDeductions;
    }

    @Override
    public List<EmployeeDeductions> addMoreToPaySlip(EmployeeDeductionsReqDto employeeDeductionsReqDto, Long emId, Long id) {
        List<EmployeeDeductions> employeeDeductionsList = new ArrayList<>();
        double totalAmount = 0.0;
        if (employeeDeductionsReqDto.getDeductions() == null ||employeeDeductionsReqDto.getDeductions().isEmpty()) {
            throw new IllegalArgumentException("Deductions list cannot be null or empty.");
        }
            List<String> deductionList = new ArrayList<>();
            Payslip payslip = payslipService.getListPaySlipByeEmIdAndCreateDate(emId,employeeDeductionsReqDto.getPaySlipDate());
            for(Long deduction: employeeDeductionsReqDto.getDeductions()){
                EmployeeDeductions employeeDeductions = new EmployeeDeductions();
                employeeDeductions.setEmpId(emId);
                employeeDeductions.setType(employeeDeductionsReqDto.getType());
                employeeDeductions.setAmount(employeeDeductionsReqDto.getAmount());
                employeeDeductions.setEffectiveDate(employeeDeductionsReqDto.getEffectiveDate());
                employeeDeductions.setDateCreated(employeeDeductionsReqDto.getDateCreated());
                employeeDeductions.setPaySlipId(payslip.getId());
                Deductions deductions = deductionsService.getDeductionsById(deduction);
                deductionList.add(deductions.getDeduction());
                employeeDeductions.setDeductions(deductions);
            }
            totalAmount+= employeeDeductionsReqDto.getAmount();
            payslipService.addDeductionsToPaySlipMore(id,totalAmount,deductionList);

        return employeeDeductionsRepo.saveAll(employeeDeductionsList);
    }

    @Transactional
    @Override
    public EmployeeDeductions update(EmployeeDeductionsReqDto2 employeeDeductionsReqDto, Long Id) {
        EmployeeDeductions employeeDeductions = getEmployeeDeductionsById(Id);
        double oldAmount = employeeDeductions.getAmount();
        double newAmount = employeeDeductionsReqDto.getAmount();
        employeeDeductions.setType(employeeDeductionsReqDto.getType());
        employeeDeductions.setAmount(employeeDeductionsReqDto.getAmount());
        employeeDeductions.setEffectiveDate(employeeDeductionsReqDto.getEffectiveDate());
        employeeDeductions.setDateCreated(employeeDeductionsReqDto.getDateCreated());
        String oldDeduction = employeeDeductions.getDeductions().getDeduction();
        String deduction = null;
        if(employeeDeductionsReqDto.getDeductions() != null){
            Deductions deductions = deductionsService.getDeductionsById(employeeDeductionsReqDto.getDeductions());
            deduction = deductions.getDeduction();
            employeeDeductions.setDeductions(deductions);
        }
        employeeDeductionsRepo.save(employeeDeductions);
        payslipService.updateDeductionsToPaySlip2(employeeDeductions.getPaySlipId(),newAmount,oldAmount,deduction,oldDeduction);
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
        return employeeDeductionsRepo.findAllByOrderByEmpDedId();
    }

    @Override
    public List<EmployeeDeductions> getListEmployeeDeductionsByEmId(Long emId) {
        return employeeDeductionsRepo.findByEmpId(emId);
    }

    @Override
    public List<EmployeeDeductionsRepDto> getListEmployeeDeductionsByPaySlipId(Long id) {
        return mapper.empDeductionsToEmpDeductionsResponseDtos(employeeDeductionsRepo.findByPaySlipId(id));
    }

    @Override
    public void deleteEmployeeDeductions(Long id) {
        EmployeeDeductions delete = getEmployeeDeductionsById(id);
        String deduction = delete.getDeductions().getDeduction();
        double deductionAmount = delete.getAmount();
        payslipService.removeDeductionFromPaySlip(delete.getPaySlipId(),deduction,deductionAmount);
        employeeDeductionsRepo.delete(delete);
    }

    @Override
    public void deleteEmpDeductionsByPlaySlip(Long id) {
        try {
            List<EmployeeDeductions> deductions = employeeDeductionsRepo.findByPaySlipId(id);

            if (deductions != null && !deductions.isEmpty()) {
                employeeDeductionsRepo.deleteAll(deductions);
            } else {
                throw  new IllegalArgumentException("No employee deductions found for pay slip ID: " + id);
            }
        } catch (Exception e) {
            throw e;  // Rethrow the exception if you want the caller to handle it
        }
    }
}
