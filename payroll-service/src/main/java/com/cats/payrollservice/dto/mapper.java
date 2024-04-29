package com.cats.payrollservice.dto;

import com.cats.payrollservice.dto.response.SalariesRepDto;
import com.cats.payrollservice.dto.response.TaxRepDto;
import com.cats.payrollservice.model.Salaries;
import com.cats.payrollservice.model.Tax;

import java.util.ArrayList;
import java.util.List;

public class mapper {
    public static TaxRepDto taxToTaxResponseDto(Tax tax){
        TaxRepDto taxRepDto = new TaxRepDto();
        taxRepDto.setId(tax.getId());
        taxRepDto.setTaxableSalary(tax.getTaxableSalary());
        taxRepDto.setRate(tax.getRate());
        return taxRepDto;
    }

    public static List<TaxRepDto> taxToTaxResponseDtos(List<Tax> taxes) {
        List<TaxRepDto> taxRepDto = new ArrayList<>();
        for (Tax tax: taxes) {
            taxRepDto.add(taxToTaxResponseDto(tax));
        }
        return taxRepDto;
    }

    public static SalariesRepDto salariesToSalariesResponseDto(Salaries salaries){
        SalariesRepDto salariesRepDto = new SalariesRepDto();
        salariesRepDto.setSalary(salaries.getSalary());
        salariesRepDto.setFromDate(salaries.getFromDate());
        salariesRepDto.setToDate(salaries.getToDate());
        salariesRepDto.setEmpId(salaries.getEmpId());
        salariesRepDto.setId(salaries.getId());
        salariesRepDto.setTax(salaries.getTax().getRate());
        return salariesRepDto;
    }

    public static List<SalariesRepDto> salariesToSalariesResponseDtos(List<Salaries> salariesList) {
        List<SalariesRepDto> salariesRepDtoList = new ArrayList<>();
        for (Salaries salaries: salariesList) {
            salariesRepDtoList.add(salariesToSalariesResponseDto(salaries));
        }
        return salariesRepDtoList;
    }
}
