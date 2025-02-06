package com.cats.payrollservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeDeductionsReqDto2 {
    private Long deductions;
    private Integer type;
    private Double amount;
    private LocalDate effectiveDate;
    private LocalDateTime dateCreated;
    private Long paySlipId;
}
