package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayrollReqDto;
import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.dto.response.PayrollPayslipResponse;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.model.Payslip;
import com.cats.payrollservice.model.Salaries;
import com.cats.payrollservice.repository.PayrollRepo;
import com.cats.payrollservice.repository.PayslipRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayrollPayslipServiceImp implements PayrollPayslipService{
    private final PayrollRepo payrollRepo;
    private final PayslipRepo payslipRepo;
    private final  PayrollService payrollService;
    private final SalariesService salariesService;
    private final TaxService taxService;

    @Transactional
    @Override
    public PayrollPayslipResponse createPayrollAndPayslip(PayrollReqDto payrollReqDto, PayslipReqDto payslipReqDto, List<Long> emIds) {
        List<Payroll> payrollList = new ArrayList<>();
        List<Payslip> payslipList = new ArrayList<>();

        for (Long emId :emIds){
            Payroll payroll = new Payroll();
            String payrollReference = payrollService.generatePayrollReference();
            payroll.setEmpId(emId);
            payroll.setRefNo(payrollReference);
            payroll.setDateFrom(payrollReqDto.getDateFrom());
            payroll.setDateTo(payrollReqDto.getDateTo());
            payroll.setDateCreate(payrollReqDto.getDateCreate());
            payroll.setType(payrollReqDto.getType());
            payroll.setStatus(payrollReqDto.getStatus());
            double net = 0.0;
            Salaries salaries = salariesService.getSalary(emId);
            if(payrollReqDto.getType() == 1){
                Double khMoney = salaries.getSalary() * payslipReqDto.getKhmerRate();
                Double tax = taxService.taxCalculator(khMoney);
                Double USDMoney = (khMoney - tax) / payslipReqDto.getKhmerRate();
                net = (USDMoney/ 2);
            }else {
                net = payrollService.calculateNetSalary(emId, payslipReqDto.getKhmerRate());
            }

            Payslip payslip = new Payslip();
            payslip.setEmpId(emId);
            payslip.setPresent(payslipReqDto.getPresent());
            payslip.setAbsent(payslipReqDto.getAbsent());
            payslip.setSalary(salaries.getSalary());
            payslip.setAllowances(payslipReqDto.getAllowances());
            payslip.setAllowanceAmount(payslipReqDto.getAllowanceAmount());
            payslip.setDeductions(payslipReqDto.getDeductions());
            payslip.setDeductionAmount(payslipReqDto.getDeductionAmount());
            payslip.setPayroll(payroll); // Associate the newly created payroll
            payslip.setDateCreated(payslipReqDto.getDateCreated());
        }

        payslipRepo.saveAll(payslipList);
        payrollRepo.saveAll(payrollList);
        return new PayrollPayslipResponse(payrollList, payslipList);
    }
}
