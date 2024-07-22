package com.cats.payrollservice.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PayrollList {
    private String emId;
    private String fullName;
    private String department;
    private String position;
    private LocalDate payrollDate;
    private String tax;
    private String grossSalary;
    private String netSalary;
    private String allowanceDec;
    private String allowanceAmount;
    private String deductionDec;
    private String deductionAmount;
    private String total;
    private String taxRate;
    private String totalSalaryUSD;
    private String totalSalaryRiel;
    private String other;
}
