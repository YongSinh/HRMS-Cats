package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.TaxReqDto;
import com.cats.payrollservice.dto.response.SalariesRepDto;
import com.cats.payrollservice.model.Salaries;
import com.cats.payrollservice.model.Tax;
import com.cats.payrollservice.repository.TaxRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TaxServiceImp implements TaxService{
    private final TaxRepo taxRepo;
   // private final  SalariesService salariesService;
    @Override
    public Tax addTax(TaxReqDto tax) {
        Tax add = new Tax();
        add.setTaxableSalary(tax.getTaxableSalary());
        add.setRate(tax.getRate());
        add.setAmount(tax.getAmount());
        add.setLowerLimit(tax.getLowerLimit());
        add.setUpperLimit(tax.getUpperLimit());
        return taxRepo.save(add);
    }

    @Override
    public Double taxCalculator( Double salary) {
        List<Tax> taxes = taxRepo.findByUpperLimitGreaterThanEqualOrderByLowerLimitAsc(salary);
        double tax = 0.0;
        double prevUpperLimit = 0.0;
        for (Tax taxTable : taxes) {
            double lowerLimit = taxTable.getLowerLimit();
            double upperLimit = taxTable.getUpperLimit();
            double rate = taxTable.getRate();

            if (salary <= lowerLimit) {
                break;
            }
            tax = (Math.min(upperLimit, salary) * rate) - taxTable.getAmount();
            if (salary <= upperLimit) {
                break;
            }

        }

        return tax;
    }

    @Override
    public Tax getTaxRateBySalary(Double salary) {
        return taxRepo.findRateBySalary(salary);
    }

    @Override
    public Tax updateTax(TaxReqDto tax, Long taxId) {
        Tax update = getTaxById(taxId);
        update.setRate(tax.getRate());
        update.setTaxableSalary(tax.getTaxableSalary());
        update.setAmount(tax.getAmount());
        update.setLowerLimit(tax.getLowerLimit());
        update.setUpperLimit(tax.getUpperLimit());
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

