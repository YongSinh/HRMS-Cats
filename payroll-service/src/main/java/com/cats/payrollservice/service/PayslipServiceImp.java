package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.model.Payslip;
import com.cats.payrollservice.repository.PayslipRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PayslipServiceImp implements PayslipService {
    private final PayslipRepo payslipRepo;
    private final PayrollService payrollService;
    @Override
    public Payslip create(PayslipReqDto payslipReqDto) {
        Payslip payslip = new Payslip();
        payslip.setEmpId(payslipReqDto.getEmpId());
        payslip.setPresent(payslipReqDto.getPresent());
        payslip.setAbsent(payslipReqDto.getAbsent());
        payslip.setSalary(payslipReqDto.getSalary());
        payslip.setAllowances(payslipReqDto.getAllowances());
        payslip.setAllowanceAmount(payslipReqDto.getAllowanceAmount());
        payslip.setDeductions(payslipReqDto.getDeductions());
        payslip.setDeductionAmount(payslipReqDto.getDeductionAmount());
        if(payslipReqDto.getPayroll() == null){
            throw new IllegalArgumentException("Please select the Payroll");
        }
        Payroll payroll = payrollService.getPayrollById(payslipReqDto.getPayroll());
        payslip.setPayroll(payroll);
        payslip.setDateCreated(payslipReqDto.getDateCreated());
        return payslip;
    }

    @Override
    public Payslip update(PayslipReqDto payslipReqDto, Long id) {
        Payslip payslip = getPaySlipById(id);
        payslip.setPresent(payslipReqDto.getPresent());
        payslip.setAbsent(payslipReqDto.getAbsent());
        payslip.setSalary(payslipReqDto.getSalary());
        payslip.setAllowances(payslipReqDto.getAllowances());
        payslip.setAllowanceAmount(payslipReqDto.getAllowanceAmount());
        payslip.setDeductions(payslipReqDto.getDeductions());
        payslip.setDeductionAmount(payslipReqDto.getDeductionAmount());
        if(payslipReqDto.getPayroll() != null){
            Payroll payroll = payrollService.getPayrollById(payslipReqDto.getPayroll());
            payslip.setPayroll(payroll);
        }
        payslip.setDateCreated(payslipReqDto.getDateCreated());
        return payslip;
    }

    @Override
    public List<Payslip> getListPaySlip() {
        return payslipRepo.findAll();
    }

    @Override
    public List<Payslip> getListPaySlipByEmId(Long emId) {
        return payslipRepo.findByEmpId(emId);
    }

    @Override
    public Payslip getPaySlipById(Long id) {
        return payslipRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        "Payslip with id: " + id + " could not be found"));
    }

    @Override
    public void delete(Long id) {
        Payslip payslip = getPaySlipById(id);
        payslipRepo.delete(payslip);
    }
}
