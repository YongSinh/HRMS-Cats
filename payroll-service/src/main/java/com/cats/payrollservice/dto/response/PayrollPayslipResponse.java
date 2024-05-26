package com.cats.payrollservice.dto.response;

import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.model.Payslip;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayrollPayslipResponse {
    private Payroll payroll;
    private Payslip payslip;
}
