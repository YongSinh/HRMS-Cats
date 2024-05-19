package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayrollReqDto;
import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.model.Payroll;
import org.springframework.stereotype.Service;


public interface PayrollService {
    Payroll getPayrollById(Long id);
    Payroll update(Long id, PayrollReqDto payrollReqDto);
    Payroll create(PayrollReqDto payrollReqDto);
    String generatePayrollReference();
}
