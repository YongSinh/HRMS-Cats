package com.cats.payrollservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaxReqDto {
    private String taxableSalary;
    private Double rate;
    private Double amount;
    private Double lowerLimit;
    private Double upperLimit;

}
