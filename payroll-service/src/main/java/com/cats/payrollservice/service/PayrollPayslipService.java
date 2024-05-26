package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayrollReqDto;
import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.dto.response.PayrollPayslipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


public interface PayrollPayslipService {
    PayrollPayslipResponse createPayrollAndPayslip(PayrollReqDto payrollReqDto, PayslipReqDto payslipReqDto);
}
