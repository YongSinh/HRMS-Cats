package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto;
import com.cats.payrollservice.dto.request.EmployeeDeductionsReqDto;
import com.cats.payrollservice.model.*;
import com.cats.payrollservice.repository.EmployeeDeductionsRepo;
import lombok.RequiredArgsConstructor;
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
    public EmployeeDeductions update(EmployeeDeductionsReqDto employeeDeductionsReqDto, Long Id) {
        EmployeeDeductions employeeDeductions = getEmployeeDeductionsById(Id);
        List<String> deductionList = new ArrayList<>();
        double oldAmount = employeeDeductions.getAmount();
        double newAmount = employeeDeductionsReqDto.getAmount();

        employeeDeductions.setType(employeeDeductionsReqDto.getType());
        employeeDeductions.setAmount(employeeDeductionsReqDto.getAmount());
        employeeDeductions.setEffectiveDate(employeeDeductionsReqDto.getEffectiveDate());
        employeeDeductions.setDateCreated(employeeDeductionsReqDto.getDateCreated());
        String oldDeduction = employeeDeductions.getDeductions().getDeduction();
        if(!employeeDeductionsReqDto.getDeductions().isEmpty()){
            for (Long deduction: employeeDeductionsReqDto.getDeductions()){
            Deductions deductions = deductionsService.getDeductionsById(deduction);
            deductionList.add(deductions.getDeduction());
            employeeDeductions.setDeductions(deductions);
            }
        }
        payslipService.updateDeductionsToPaySlip(employeeDeductions.getPaySlipId(),newAmount,oldAmount,deductionList,oldDeduction);
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
    public List<EmployeeDeductions> getListEmployeeDeductionsByPaySlipId(Long id) {
        return employeeDeductionsRepo.findByPaySlipId(id);
    }

    @Override
    public void deleteEmployeeDeductions(Long id) {
        EmployeeDeductions delete = getEmployeeDeductionsById(id);
        String deduction = delete.getDeductions().getDeduction();
        double deductionAmount = delete.getAmount();
        payslipService.removeDeductionFromPaySlip(delete.getPaySlipId(),deduction,deductionAmount);
        employeeDeductionsRepo.delete(delete);
    }
}
