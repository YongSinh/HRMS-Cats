package com.cats.payrollservice.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaxRepDto {
    private Long id;
    private String taxableSalary;
    private Double rate;
    private Double amount;
    private Double lowerLimit;
    private Double upperLimit;
    private String salariesList;
}
