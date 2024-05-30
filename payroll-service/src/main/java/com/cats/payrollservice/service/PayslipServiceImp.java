package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.dto.response.SalariesRepDto;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.model.Payslip;
import com.cats.payrollservice.model.Salaries;
import com.cats.payrollservice.repository.PayslipRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
public class PayslipServiceImp implements PayslipService {
    private final PayslipRepo payslipRepo;
    private final PayrollService payrollService;
    private final SalariesService salariesService;
    private final TaxService taxService;
    private final  ServiceCalculate serviceCalculate;
    @Override
    public List<Payslip> create(PayslipReqDto payslipReqDto, List<Long> emId) {
        List<Payslip> payslipList = new ArrayList<>();
        for (Long emIds : emId){
            double net=0.0;
            double Total=0.0;
            double allowanceAmount=0.0;
            double deductionAmount=0.0;
            SalariesRepDto salaries = salariesService.getSalaryByEmId(emIds);
            Payslip payslip = new Payslip();
            payslip.setEmpId(emIds);
            payslip.setPresent(payslipReqDto.getPresent());
            payslip.setAbsent(payslipReqDto.getAbsent());
            if(payslipReqDto.getPayrollDate() == null){
                throw new IllegalArgumentException("Please select the Payroll");
            }
            Payroll payroll = payrollService.getPayrollById(payslipReqDto.getPayroll());
            payslip.setPayroll(payroll);
            payslip.setPayType(payslipReqDto.getPaymentType());
            if(payslipReqDto.getPaymentType() == 1){
                Double khMoney = salaries.getSalary() * payslipReqDto.getKhmerRate();
                Double tax = taxService.taxCalculator(khMoney);
                Double USDMoney = (khMoney - tax) / payslipReqDto.getKhmerRate();
                allowanceAmount= payslipReqDto.getAllowanceAmount(); ;
                deductionAmount = payslipReqDto.getDeductionAmount();
                USDMoney+= allowanceAmount;
                USDMoney-= deductionAmount;
                net = USDMoney / 2;

            }else {
                net = payrollService.calculateNetSalary(emIds, payslipReqDto.getKhmerRate());
            }
            net = serviceCalculate.roundUp(net);
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
    public Payslip getListPaySlipByeEmIdAndCreateDate(Long emId, LocalDate localDate) {
        return payslipRepo.findByEmpIdAndDateCreated(emId,localDate);
    }

    @Override
    public void addAllowanceToPaySlip(Long emId, LocalDate localDate, Double amount, List<String> allowance) {
        Payslip  addAllowance = getListPaySlipByeEmIdAndCreateDate(emId, localDate);
        StringJoiner joiner = new StringJoiner(",");
        for (String s : allowance) {
            joiner.add(s);
        }
        System.out.println(joiner.toString());
        addAllowance.setAllowances(allowance.toString());
        addAllowance.setAllowanceAmount(amount);
        double net = amount + addAllowance.getNet();
        net = serviceCalculate.roundUp(net);
        addAllowance.setNet(net);
        payslipRepo.save(addAllowance);
    }

    @Override
    public Payslip addDeductionsToPaySlip(Long emId, LocalDate localDate, Double amount, List<String> deductions) {
        Payslip  addDeductions = getListPaySlipByeEmIdAndCreateDate(emId, localDate);
        StringJoiner joiner = new StringJoiner(",");
        for (String s : deductions) {
            joiner.add(s);
        }
        addDeductions.setDeductions(joiner.toString());
        addDeductions.setDeductionAmount(amount);
        double net = amount - addDeductions.getNet();
        net = serviceCalculate.roundUp(net);
        addDeductions.setNet(net);
        payslipRepo.save(addDeductions);
        return addDeductions;
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
