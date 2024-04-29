package com.cats.payrollservice.service;

import com.cats.payrollservice.model.Tax;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaxService {
    Tax addTax(Tax tax);
    Tax updateTax(Tax tax, Long taxId);
    List<Tax> getListTax();
    Tax getTaxById(Long id);
    void deleteTax(Long id);
}

