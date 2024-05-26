package com.cats.payrollservice.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class PayslipReqDto {
    private Long payroll;
    private Long empId;
    private Integer present;
    private Integer absent;
    private Double salary;
    private Double allowanceAmount;
    private String allowances;
    private Double deductionAmount;
    private String deductions;
    private LocalDateTime dateCreated;
    private Double khmerRate;
}
