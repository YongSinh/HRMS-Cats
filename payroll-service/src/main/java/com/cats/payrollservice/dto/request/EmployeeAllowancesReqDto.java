package com.cats.payrollservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeAllowancesReqDto {
    private List<Long> allowances;
    private Integer type;
    private Double amount;
    private LocalDate effectiveDate;
    private LocalDateTime dateCreated;
    private LocalDate paySlipDate;
    private Long paySlipId;

}
