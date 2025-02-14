package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.model.Payslip;

import java.time.LocalDate;
import java.util.List;


public interface PayslipService {

    List<Payslip> create(PayslipReqDto payslipReqDto);

    List<Payslip> getPayslipsByDateRange(LocalDate startDate, LocalDate endDate);

    Payslip update(PayslipReqDto payslipReqDto, Long id);

    List<Payslip> getListPaySlip();

    Payslip getListPaySlipByeEmIdAndCreateDate(Long emId, LocalDate localDate);

    void addAllowanceToPaySlip(Long emId, LocalDate localDate, Double amount, List<String> allowance);

    void addAllowanceToPaySlipMore(Long id, Double newAmount, List<String> allowance);

    void addAllowanceToPaySlipMore2(Long id, Double newAmount, String allowance);

    void updateAllowanceToPaySlip(Long id, Double newAmount, Double oldAmount, List<String> allowance, String oldAllowance);

    void updateAllowanceToPaySlip2(Long id, Double newAmount, Double oldAmount, String allowance, String oldAllowance);

    void addDeductionsToPaySlip(Long emId, LocalDate localDate, Double amount, List<String> deductions);

    void addDeductionsToPaySlipMore(Long id, Double newAmount, List<String> deductions);

    void addDeductionsToPaySlipMore2(Long id, Double newAmount, String deductions);

    void updateDeductionsToPaySlip(Long id, Double newAmount, Double oldAmount, List<String> deductions, String oldDeductions);

    void updateDeductionsToPaySlip2(Long id, Double newAmount, Double oldAmount, String deductions, String oldDeductions);

    void removeDeductionFromPaySlip(Long id, String deductionToRemove, Double amountToRemove);

    void removeAllowanceFromPaySlip(Long id, String allowanceToRemove, Double amountToRemove);

    List<Payslip> getListPaySlipByEmId(Long emId);

    Payslip getPaySlipById(Long id);

    void delete(Long id);

}
