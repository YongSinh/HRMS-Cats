package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayrollReqDto;
import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.model.Payroll;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


public interface PayrollService {
    Payroll getPayrollById(Long id);
    List<Payroll> update(Long id, PayrollReqDto payrollReqDto, List<Long> emIds);
    List<Payroll> create(PayrollReqDto payrollReqDto, List<Long> emIds);
    String generatePayrollReference();
    List<Payroll> getListPayroll();
    List<Payroll> getListPayRollByEmId(Long emId);
    Payroll getListPayRollByEmIdAndCreateDate(Long emId, LocalDate date);
    Double calculateNetSalary(Long emId, Double KhRate);
    List<Payroll> findPayRollByDepEmId(Long depId);
    void deletePayroll(Long id);
}
