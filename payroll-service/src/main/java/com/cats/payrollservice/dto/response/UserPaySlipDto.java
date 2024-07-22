package com.cats.payrollservice.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserPaySlipDto {
    private String emId;
    private String fullName;
    private LocalDate payrollDate;
    private String tax;
    private String basSalary;
    private String netSalary;
    private String allowanceDec;
    private String allowanceAmount;
    private String deductionDec;
    private String deductionAmount;
    private String other;
}
