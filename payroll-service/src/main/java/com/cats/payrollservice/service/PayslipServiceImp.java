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
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

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
            SalariesRepDto salaries = salariesService.getSalaryByEmId(emIds);
            Payslip payslip = new Payslip();
            payslip.setEmpId(emIds);
            if(payslipReqDto.getPayrollDate() == null){
                throw new IllegalArgumentException("Please select the Payroll");
            }
            Payroll payroll = payrollService.getPayRollByEmIdAndCreateDate(emIds, payslipReqDto.getPayrollDate());
            payslip.setPayroll(payroll);
            payslip.setPayType(payslipReqDto.getPaymentType());
            if(payslipReqDto.getPaymentType() == 1){
                Double khMoney = salaries.getSalary() * payslipReqDto.getKhmerRate();
                Double tax = taxService.taxCalculator(khMoney);
                Double USDMoney = (khMoney - tax) / payslipReqDto.getKhmerRate();
                net = USDMoney / 2;

            }else if (payslipReqDto.getPaymentType() == 2){
                net = salaries.getSalary() / 2;
            }
            else {
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
    public List<Payslip> update(PayslipReqDto payslipReqDto, Long id, List<Long> emId) {
        List<Payslip> payslipList = new ArrayList<>();
        for (Long emIds : emId){
        Salaries salaries = salariesService.getSalary(emIds);
        double salary=0.0;
        Payslip payslip = getPaySlipById(id);
        payslip.setAllowances(payslipReqDto.getAllowances());
        payslip.setAllowanceAmount(payslipReqDto.getAllowanceAmount());
        payslip.setDeductions(payslipReqDto.getDeductions());
        payslip.setDeductionAmount(payslipReqDto.getDeductionAmount());
        Payroll payroll = payrollService.getPayRollByEmIdAndCreateDate(emIds, payslipReqDto.getPayrollDate());
        payslip.setPayroll(payroll);
        if(payroll.getType() == 1){
            Double khMoney = salaries.getSalary() * payslipReqDto.getKhmerRate();
            Double tax = taxService.taxCalculator(khMoney);
            Double USDMoney = (khMoney - tax) / payslipReqDto.getKhmerRate();
            salary = (USDMoney/ 2);
        }
        else {
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
        addAllowance.setAllowances(joiner.toString());
        addAllowance.setAllowanceAmount(amount);
        double net = amount + addAllowance.getNet();
        net = serviceCalculate.roundUp(net);
        addAllowance.setNet(net);
        payslipRepo.save(addAllowance);
    }

    @Override
    public void addAllowanceToPaySlipMore(Long id, Double newAmount, List<String> allowance){
        Payslip  update = getPaySlipById(id);
        double net = 0.0;
        List<String> allowanceList = convertStringToList(update.getAllowances());
        System.out.println(allowanceList.toString());
        for (String allowances: allowance){
            for (int i = 0; i < allowanceList.size(); i++) {
                    allowanceList.add(i, allowances);
            }
        }

        String updatedAllowances = String.join(", ", allowanceList);
        double result =update.getAllowanceAmount() + newAmount;
         net = update.getNet() + result;
        // int finalResult = result ;
        System.out.println(result);
        System.out.println(net);
        update.setAllowanceAmount(result);
        update.setAllowances(updatedAllowances);
        update.setNet(net);
        update.setAllowanceAmount(result);
        payslipRepo.save(update);
    }

    @Override
    public void updateAllowanceToPaySlip(Long id, Double newAmount, Double oldAmount, List<String> allowance, String oldAllowance) {
        Payslip  update = getPaySlipById(id);
        double net = 0.0;
        List<String> allowanceList = convertStringToList(update.getAllowances());
        for (String allowances: allowance){
            for (int i = 0; i < allowanceList.size(); i++) {
                if (allowanceList.get(i).equalsIgnoreCase(oldAllowance.trim())) {
                    allowanceList.set(i, allowances);
                }
            }
        }

        String updatedAllowances = String.join(", ", allowanceList);
        System.out.println(updatedAllowances);
        double result =update.getAllowanceAmount() - oldAmount;
        double finalResult = result + newAmount;
        net = update.getNet() - update.getAllowanceAmount();
        double finalNet = net + finalResult;
       // int finalResult = result ;
        update.setNet(finalNet);
        update.setAllowances(updatedAllowances);
        update.setAllowanceAmount(finalResult);
        payslipRepo.save(update);
    }

    public List<String> convertStringToList(String allowances) {
        return Arrays.stream(allowances.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Override
    public void addDeductionsToPaySlip(Long emId, LocalDate localDate, Double amount, List<String> deductions) {
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
    }

    @Override
    public void addDeductionsToPaySlipMore(Long id, LocalDate localDate, Double newAmount, Double oldAmount, List<String> deductions, String oldDeductions) {

    }

    @Override
    public void updateDeductionsToPaySlip(Long id, LocalDate localDate,Double newAmount, Double oldAmount, List<String> deductions, String oldDeductions) {
        Payslip  update = getPaySlipById(id);
        double net = 0.0;
        List<String> deductionList = convertStringToList(update.getAllowances());
        for (String deduction: deductions){
            for (int i = 0; i < deductionList.size(); i++) {
                if (deductionList.get(i).equalsIgnoreCase(oldDeductions.trim())) {
                    deductionList.set(i, deduction);
                }
            }
        }

        String updatedDeduction = String.join(", ", deductionList);
        double result =update.getAllowanceAmount() - oldAmount;
        double finalResult = result + newAmount;
        net = update.getNet() - update.getAllowanceAmount();
        double finalNet = net - finalResult;
        // int finalResult = result ;
        update.setNet(finalNet);
        update.setDeductions(updatedDeduction);
        update.setDeductionAmount(  finalResult);
        payslipRepo.save(update);
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
