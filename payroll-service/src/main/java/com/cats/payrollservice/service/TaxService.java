package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.TaxReqDto;
import com.cats.payrollservice.model.Tax;

import java.util.List;


public interface TaxService {
    Tax addTax(TaxReqDto tax);

    Tax updateTax(TaxReqDto tax, Long taxId);

    List<Tax> getListTax();

    Tax getTaxById(Long id);

    void deleteTax(Long id);

    Double taxCalculator(Double salary);

    Tax getTaxRateBySalary(Double salary);
}

