package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayrollReqDto;
import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.model.Payroll;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


public interface PayrollService {
    Payroll getPayrollById(Long id);
    List<Payroll> update(Long id, PayrollReqDto payrollReqDto);
    List<Payroll> create(PayrollReqDto payrollReqDto);
    String generatePayrollReference();
    List<Payroll> getListPayroll();
    List<Payroll> getListPayRollByEmId(Long emId);
    List<Payroll> getListPayRollByEmIdAndCreateDate(Long emId, LocalDate date);

    List<Payroll> findPayRollByDepEmId(Long depId);
    void deletePayroll(Long id);
}
