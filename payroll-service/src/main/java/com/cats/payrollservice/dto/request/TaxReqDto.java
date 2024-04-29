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
    private Long id;
    private String taxableSalary;
    private Double rate;
    private String salariesList;
}
