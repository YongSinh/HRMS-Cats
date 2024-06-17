package com.cats.payrollservice.dto.response;
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
public class EmployeeAllowancesRepDto {
    private Long empAllId;
    private Long empId;
    private String allowances;
    private Long allowancesId;
    private Integer type;
    private Double amount;
    private LocalDate effectiveDate;
    private LocalDateTime dateCreated;
    private Long paySlipId;
}
