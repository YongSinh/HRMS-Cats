package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.model.Payslip;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface PayslipService {

    List<Payslip> create(PayslipReqDto payslipReqDto, List<Long> emId);
    List<Payslip>  update(PayslipReqDto payslipReqDto, Long id, List<Long> emId);
    List<Payslip> getListPaySlip();
    Payslip getListPaySlipByeEmIdAndCreateDate(Long emId, LocalDate localDate);
    void addAllowanceToPaySlip(Long emId, LocalDate localDate, Double amount, List<String> allowance);
    void updateAllowanceToPaySlip(Long id, Double newAmount, Double oldAmount,List<String> allowance, String oldAllowance);
    Payslip addDeductionsToPaySlip(Long emId, LocalDate localDate,Double amount, List<String> deductions);
    List<Payslip> getListPaySlipByEmId(Long emId);
    Payslip getPaySlipById(Long id);

    void delete(Long id);

}
