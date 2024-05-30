package com.cats.payrollservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SalariesReqDto {
    private Long empId;
    private Double salary;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Long taxId;

}
