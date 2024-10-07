package com.cats.payrollservice.dto;

import com.cats.payrollservice.dto.response.EmployeeAllowancesRepDto;
import com.cats.payrollservice.dto.response.EmployeeDeductionsRepDto;
import com.cats.payrollservice.dto.response.SalariesRepDto;
import com.cats.payrollservice.dto.response.TaxRepDto;
import com.cats.payrollservice.model.EmployeeAllowances;
import com.cats.payrollservice.model.EmployeeDeductions;
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
        taxRepDto.setAmount(tax.getAmount());
        taxRepDto.setLowerLimit(tax.getLowerLimit());
        taxRepDto.setUpperLimit(tax.getUpperLimit());
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

    public static EmployeeAllowancesRepDto employeeAllowancesToEmployeeAllowancesResponseDto(EmployeeAllowances employeeAllowances){
        EmployeeAllowancesRepDto employeeAllowancesRepDto = new EmployeeAllowancesRepDto();
        employeeAllowancesRepDto.setEmpAllId(employeeAllowances.getEmpAllId());
        employeeAllowancesRepDto.setEmpId(employeeAllowances.getEmpId());
        employeeAllowancesRepDto.setType(employeeAllowances.getType());
        employeeAllowancesRepDto.setAmount(employeeAllowances.getAmount());
        employeeAllowancesRepDto.setEffectiveDate(employeeAllowances.getEffectiveDate());
        employeeAllowancesRepDto.setDateCreated(employeeAllowances.getDateCreated());
        employeeAllowancesRepDto.setAllowances(employeeAllowances.getAllowances().getAllowances());
        employeeAllowancesRepDto.setAllowancesId(employeeAllowances.getAllowances().getALLId());
        employeeAllowancesRepDto.setPaySlipId(employeeAllowances.getPaySlipId());
        return employeeAllowancesRepDto;
    }

    public static List<EmployeeAllowancesRepDto> employeeAllowancesToEmployeeAllowancesResponseDtos(List<EmployeeAllowances> employeeAllowancesList){
        List<EmployeeAllowancesRepDto> employeeAllowancesRepDtos = new ArrayList<>();
        for (EmployeeAllowances employeeAllowances : employeeAllowancesList){
            employeeAllowancesRepDtos.add(employeeAllowancesToEmployeeAllowancesResponseDto(employeeAllowances));
        }
        return employeeAllowancesRepDtos;
    }

    public static EmployeeDeductionsRepDto empDeductionsToEmpDeductionsResponseDto(EmployeeDeductions deductions){
        EmployeeDeductionsRepDto employeeDeductionsRepDto = new EmployeeDeductionsRepDto();
        employeeDeductionsRepDto.setEmpDedId(deductions.getEmpDedId());
        employeeDeductionsRepDto.setEmpId(deductions.getEmpId());
        employeeDeductionsRepDto.setType(deductions.getType());
        employeeDeductionsRepDto.setAmount(deductions.getAmount());
        employeeDeductionsRepDto.setDeductions(deductions.getDeductions().getDeduction());
        employeeDeductionsRepDto.setEffectiveDate(deductions.getEffectiveDate());
        employeeDeductionsRepDto.setDateCreated(deductions.getDateCreated());
        return employeeDeductionsRepDto;
    }

    public static List<EmployeeDeductionsRepDto> empDeductionsToEmpDeductionsResponseDtos(List<EmployeeDeductions> deductions){
        List<EmployeeDeductionsRepDto> employeeDeductionsRepDto = new ArrayList<>();
        for (EmployeeDeductions employeeDeductions : deductions){
            employeeDeductionsRepDto.add(empDeductionsToEmpDeductionsResponseDto(employeeDeductions));
        }
        return employeeDeductionsRepDto;
    }



}
