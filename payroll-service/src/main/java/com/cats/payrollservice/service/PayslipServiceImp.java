package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.model.Payslip;
import com.cats.payrollservice.model.Salaries;
import com.cats.payrollservice.repository.PayslipRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayslipServiceImp implements PayslipService {
    private final PayslipRepo payslipRepo;
    private final PayrollService payrollService;
    private final SalariesService salariesService;
    private final TaxService taxService;
    @Override
    public List<Payslip> create(PayslipReqDto payslipReqDto, List<Long> emId) {
        List<Payslip> payslipList = new ArrayList<>();
        for (Long emIds : emId){
            Salaries salaries = salariesService.getSalary(emIds);
            double net=0.0;
            Payslip payslip = new Payslip();
            payslip.setEmpId(emIds);
            payslip.setPresent(payslipReqDto.getPresent());
            payslip.setAbsent(payslipReqDto.getAbsent());
            payslip.setAllowances(payslipReqDto.getAllowances());
            payslip.setAllowanceAmount(payslipReqDto.getAllowanceAmount());
            payslip.setDeductions(payslipReqDto.getDeductions());
            payslip.setDeductionAmount(payslipReqDto.getDeductionAmount());
            if(payslipReqDto.getPayrollDate() == null){
                throw new IllegalArgumentException("Please select the Payroll");
            }
            Payroll payroll = payrollService.getListPayRollByEmIdAndCreateDate(emIds, payslipReqDto.getPayrollDate());
            payslip.setPayroll(payroll);
            if(payslipReqDto.getPaymentType() == 1){
                Double khMoney = salaries.getSalary() * payslipReqDto.getKhmerRate();
                Double tax = taxService.taxCalculator(khMoney);
                Double USDMoney = (khMoney - tax) / payslipReqDto.getKhmerRate();
                net = (USDMoney/ 2);
            }else {
                net = payrollService.calculateNetSalary(emIds, payslipReqDto.getKhmerRate());
            }
            payslip.setSalary(salaries.getSalary());
            payslip.setNet(net);
            payslip.setDateCreated(payslipReqDto.getDateCreated());
            payslipList.add(payslip);
        }
        payslipRepo.saveAll(payslipList);
        return payslipList;
    }

    @Override
    public List<Payslip>  update(PayslipReqDto payslipReqDto, Long id, List<Long> emId) {
        List<Payslip> payslipList = new ArrayList<>();
        for (Long emIds : emId){
        Salaries salaries = salariesService.getSalary(emIds);
        double salary=0.0;
        Payslip payslip = getPaySlipById(id);
        payslip.setPresent(payslipReqDto.getPresent());
        payslip.setAbsent(payslipReqDto.getAbsent());
        payslip.setAllowances(payslipReqDto.getAllowances());
        payslip.setAllowanceAmount(payslipReqDto.getAllowanceAmount());
        payslip.setDeductions(payslipReqDto.getDeductions());
        payslip.setDeductionAmount(payslipReqDto.getDeductionAmount());
        Payroll payroll = payrollService.getPayrollById(payslipReqDto.getPayroll());
        if(payslipReqDto.getPayroll() != null){
            payslip.setPayroll(payroll);
        }
        if(payroll.getType() == 1){
            Double khMoney = salaries.getSalary() * payslipReqDto.getKhmerRate();
            Double tax = taxService.taxCalculator(khMoney);
            Double USDMoney = (khMoney - tax) / payslipReqDto.getKhmerRate();
            salary = (USDMoney/ 2);
        }else {
            salary = payrollService.calculateNetSalary(emIds, payslipReqDto.getKhmerRate());
        }
        payslip.setSalary(salary);
        payslip.setDateCreated(payslipReqDto.getDateCreated());}
        payslipRepo.saveAll(payslipList);
        return payslipList;
    }

    @Override
    public List<Payslip> getListPaySlip() {
        return payslipRepo.findAll();
    }

    @Override
    public Payslip getListPaySlipByeEmIdAndCreateDate(Long emId, LocalDateTime localDateTime) {
        return payslipRepo.findByEmpIdAndDateCreated(emId,localDateTime);
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
