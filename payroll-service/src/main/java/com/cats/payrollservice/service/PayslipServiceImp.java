package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.dto.response.SalariesRepDto;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.model.Payslip;
import com.cats.payrollservice.model.Salaries;
import com.cats.payrollservice.repository.PayslipRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@EnableTransactionManagement
@Lazy
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
    public Payslip update(PayslipReqDto payslipReqDto, Long id) {
        double salary=0.0;
        Payslip payslip = getPaySlipById(id);
        Salaries salaries = salariesService.getSalary(payslip.getEmpId());
        payslip.setAllowances(payslipReqDto.getAllowances());
        payslip.setAllowanceAmount(payslipReqDto.getAllowanceAmount());
        payslip.setDeductions(payslipReqDto.getDeductions());
        payslip.setDeductionAmount(payslipReqDto.getDeductionAmount());
        Payroll payroll = payrollService.getPayRollByEmIdAndCreateDate(payslip.getEmpId(), payslipReqDto.getPayrollDate());
        payslip.setPayroll(payroll);
        if(payroll.getType() == 1){
            Double khMoney = salaries.getSalary() * payslipReqDto.getKhmerRate();
            Double tax = taxService.taxCalculator(khMoney);
            Double USDMoney = (khMoney - tax) / payslipReqDto.getKhmerRate();
            salary = (USDMoney/ 2);
        }
        else {
            salary = payrollService.calculateNetSalary(payslip.getEmpId(), payslipReqDto.getKhmerRate());
        }
        payslip.setSalary(salary);
        payslip.setDateCreated(payslipReqDto.getDateCreated());
        payslipRepo.save(payslip);
        return payslip;
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
    public void addAllowanceToPaySlipMore(Long id, Double newAmount, List<String> allowances) {
        Payslip update = getPaySlipById(id);
        if (update == null) {
            throw new IllegalArgumentException("Payslip with id " + id + " not found.");
        }

        // Convert the allowances string to a list
        List<String> allowanceList = convertStringToList(update.getAllowances());

        // Add new allowances to the list
        allowanceList.addAll(allowances);

        // Join the updated allowance list back to a string
        String updatedAllowances = String.join(", ", allowanceList);

        // Calculate the new allowance amount
        double updatedAllowanceAmount = update.getAllowanceAmount() + newAmount;

        // Calculate the new net amount
        double newNet = update.getNet() + newAmount;

        // Set the updated values to the payslip
        update.setAllowanceAmount(updatedAllowanceAmount);
        update.setAllowances(updatedAllowances);
        update.setNet(newNet);

        // Save the updated payslip
        payslipRepo.save(update);
    }

    @Override
    public void updateAllowanceToPaySlip(Long id, Double newAmount, Double oldAmount, List<String> allowances, String oldAllowance) {
        Payslip update = getPaySlipById(id);
        if (update == null) {
            throw new IllegalArgumentException("Payslip with id " + id + " not found.");
        }

        // Convert the allowances string to a list
        List<String> allowanceList = convertStringToList(update.getAllowances());

        // Update the allowance list
        for (int i = 0; i < allowanceList.size(); i++) {
            if (allowanceList.get(i).equalsIgnoreCase(oldAllowance.trim())) {
                // Replace the old allowance with the new one
                if (!allowances.isEmpty()) {
                    allowanceList.set(i, allowances.remove(0));
                } else {
                    // If newAllowances list is exhausted, break the loop
                    break;
                }
            }
        }


        // Join the updated allowance list back to a string
        String updatedAllowances = String.join(", ", allowanceList);

        // Calculate the new allowance amount
        double updatedAllowanceAmount = update.getAllowanceAmount() - oldAmount + newAmount;

        // Calculate the new net amount
        double newNet = update.getNet() - update.getAllowanceAmount() + updatedAllowanceAmount;

        // Set the updated values to the payslip
        update.setNet(newNet);
        update.setAllowances(updatedAllowances);
        update.setAllowanceAmount(updatedAllowanceAmount);

        // Save the updated payslip
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
    public void addDeductionsToPaySlipMore(Long id, Double newAmount, List<String> deductions) {
        Payslip update = getPaySlipById(id);
        if (update == null) {
            throw new IllegalArgumentException("Payslip with id " + id + " not found.");
        }

        // Convert the deductions string to a list
        List<String> deductionList = convertStringToList(update.getDeductions());

        // Add new deductions to the list
        deductionList.addAll(deductions);

        // Join the updated deduction list back to a string
        String updatedDeductions = String.join(", ", deductionList);

        // Calculate the new deduction amount
        double updatedDeductionAmount = update.getDeductionAmount() + newAmount;

        // Calculate the new net amount
        double newNet = update.getNet() - newAmount;

        // Set the updated values to the payslip
        update.setDeductions(updatedDeductions);
        update.setDeductionAmount(updatedDeductionAmount);
        update.setNet(newNet);

        // Save the updated payslip
        payslipRepo.save(update);
    }

    @Override
    public void updateDeductionsToPaySlip(Long id, Double newAmount, Double oldAmount, List<String> deductions, String oldDeductions) {
        Payslip update = getPaySlipById(id);
        if (update == null) {
            throw new IllegalArgumentException("Payslip with id " + id + " not found.");
        }

        // Convert the deductions string to a list
        List<String> deductionList = convertStringToList(update.getDeductions());

        for (int i = 0; i < deductionList.size(); i++) {
            if (deductionList.get(i).equalsIgnoreCase(oldDeductions.trim())) {
                // Replace the old allowance with the new one
                if (!deductions.isEmpty()) {
                    deductionList.set(i, deductions.remove(0));
                } else {
                    // If newAllowances list is exhausted, break the loop
                    break;
                }
            }
        }

        // Join the updated deduction list back to a string
        String updatedDeduction = String.join(", ", deductionList);

        // Calculate the new deduction amount
        double updatedDeductionAmount = update.getDeductionAmount() - oldAmount + newAmount;

        // Calculate the new net amount
        double newNet = update.getNet() + oldAmount - newAmount;

        // Set the updated values to the payslip
        update.setNet(newNet);
        update.setDeductions(updatedDeduction);
        update.setDeductionAmount(updatedDeductionAmount);

        // Save the updated payslip
        payslipRepo.save(update);
    }

    @Override
    public void removeDeductionFromPaySlip(Long id, String deductionToRemove, Double amountToRemove) {
        // Retrieve the payslip by ID
        Payslip update = getPaySlipById(id);
        if (update == null) {
            throw new IllegalArgumentException("Payslip with id " + id + " not found.");
        }

        // Convert the deductions string to a list
        List<String> deductionList = convertStringToList(update.getDeductions());

        // Remove the specified deduction
        boolean removed = deductionList.removeIf(deduction -> deduction.equalsIgnoreCase(deductionToRemove.trim()));

        // If the deduction was removed, update the amounts
        if (removed) {
            // Join the updated deduction list back to a string
            String updatedDeduction = String.join(", ", deductionList);

            // Calculate the new deduction amount
            double updatedDeductionAmount = update.getDeductionAmount() - amountToRemove;

            // Calculate the new net amount
            double newNet = update.getNet() + amountToRemove;

            // Set the updated values to the payslip
            update.setNet(newNet);
            update.setDeductions(updatedDeduction);
            update.setDeductionAmount(updatedDeductionAmount);

            // Save the updated payslip
            payslipRepo.save(update);
        } else {
            throw new IllegalArgumentException("Deduction " + deductionToRemove + " not found in the payslip.");
        }
    }

    @Override
    public void removeAllowanceFromPaySlip(Long id, String allowanceToRemove, Double amountToRemove) {
        Payslip update = getPaySlipById(id);
        if (update == null) {
            throw new IllegalArgumentException("Payslip with id " + id + " not found.");
        }

        // Convert the allowances string to a list
        List<String> allowanceList = convertStringToList(update.getAllowances());

        // Remove the specified allowance
        boolean removed = allowanceList.removeIf(allowance -> allowance.equalsIgnoreCase(allowanceToRemove.trim()));

        // If the allowance was removed, update the amounts
        if (removed) {
            // Join the updated allowance list back to a string
            String updatedAllowances = String.join(", ", allowanceList);

            // Calculate the new allowance amount
            double updatedAllowanceAmount = update.getAllowanceAmount() - amountToRemove;

            // Calculate the new net amount
            double newNet = update.getNet() - amountToRemove;

            // Set the updated values to the payslip
            update.setNet(newNet);
            update.setAllowances(updatedAllowances);
            update.setAllowanceAmount(updatedAllowanceAmount);

            // Save the updated payslip
            payslipRepo.save(update);
        } else {
            throw new IllegalArgumentException("Allowance " + allowanceToRemove + " not found in the payslip.");
        }
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
        //employeeAllowancesService.deleteEmpAllowanceByPaySlipId(id);
       // employeeDeductionsService.deleteEmployeeDeductions(id);
        payslipRepo.delete(payslip);
    }
}
