package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayrollReqDto;
import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.dto.response.PayrollPayslipResponse;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.model.Payslip;
import com.cats.payrollservice.repository.PayrollRepo;
import com.cats.payrollservice.repository.PayslipRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayrollPayslipServiceImp implements PayrollPayslipService{

    private final PayrollRepo payrollRepo;
    private final PayslipRepo payslipRepo;
    private final  PayrollService payrollService;
    private Process log;

    @Transactional
    @Override
    public PayrollPayslipResponse createPayrollAndPayslip(PayrollReqDto payrollReqDto, PayslipReqDto payslipReqDto) {
        Payroll payroll = new Payroll();
        String payrollReference = payrollService.generatePayrollReference();
        payroll.setEmpId(payrollReqDto.getEmpId());
        payroll.setRefNo(payrollReference);
        payroll.setDateFrom(payrollReqDto.getDateFrom());
        payroll.setDateTo(payrollReqDto.getDateTo());
        payroll.setDateCreate(payrollReqDto.getDateCreate());
        payroll.setType(payrollReqDto.getType());
        payroll.setStatus(payrollReqDto.getStatus());
        payrollRepo.save(payroll);

        Payslip payslip = new Payslip();
        payslip.setEmpId(payslipReqDto.getEmpId());
        payslip.setPresent(payslipReqDto.getPresent());
        payslip.setAbsent(payslipReqDto.getAbsent());
        payslip.setSalary(payslipReqDto.getSalary());
        payslip.setAllowances(payslipReqDto.getAllowances());
        payslip.setAllowanceAmount(payslipReqDto.getAllowanceAmount());
        payslip.setDeductions(payslipReqDto.getDeductions());
        payslip.setDeductionAmount(payslipReqDto.getDeductionAmount());
        payslip.setPayroll(payroll); // Associate the newly created payroll
        payslip.setDateCreated(payslipReqDto.getDateCreated());
        payslipRepo.save(payslip);

        return new PayrollPayslipResponse(payroll, payslip);
    }
}
