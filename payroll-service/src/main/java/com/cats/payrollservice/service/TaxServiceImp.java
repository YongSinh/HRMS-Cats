package com.cats.payrollservice.service;

import com.cats.payrollservice.model.Tax;
import com.cats.payrollservice.repository.TaxRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
public class TaxServiceImp implements TaxService{
    private final TaxRepo taxRepo;
    @Override
    public Tax addTax(Tax tax) {
        Tax add = new Tax();
        add.setTaxableSalary(tax.getTaxableSalary());
        add.setRate(tax.getRate());
        return taxRepo.save(add);
    }

    @Override
    public Tax updateTax(Tax tax, Long taxId) {
        Tax update = getTaxById(taxId);
        update.setRate(tax.getRate());
        update.setTaxableSalary(tax.getTaxableSalary());
        return taxRepo.save(update);
    }

    @Override
    public List<Tax> getListTax() {
        return taxRepo.findAll();
    }

    @Override
    public Tax getTaxById(Long id) {
        return taxRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        "Tax with id: " + id + " could not be found"));

    }

    @Override
    public void deleteTax(Long id) {
        Tax delete = getTaxById(id);
        taxRepo.delete(delete);
    }
}

