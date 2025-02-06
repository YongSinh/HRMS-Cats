package com.cats.payrollservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SalariesRepDto {
    private Long id;
    private Long empId;
    private Double salary;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Double tax;

}
