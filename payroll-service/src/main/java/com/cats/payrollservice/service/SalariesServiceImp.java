package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.mapper;
import com.cats.payrollservice.dto.request.SalariesReqDto;
import com.cats.payrollservice.dto.response.SalariesRepDto;
import com.cats.payrollservice.model.Salaries;
import com.cats.payrollservice.model.Tax;
import com.cats.payrollservice.repository.SalariesRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class SalariesServiceImp implements SalariesService {
    private final SalariesRepo salariesRepo;
    private final TaxService taxService;

    @Transactional
    @Override
    public SalariesRepDto addSalary(SalariesReqDto salariesReqDto) {
        Salaries salaries = new Salaries();
        salaries.setSalary(salariesReqDto.getSalary());
        salaries.setFromDate(salariesReqDto.getFromDate());
        salaries.setToDate(salariesReqDto.getToDate());
        if (salariesReqDto.getTaxId() == null) {
            throw new IllegalArgumentException("Tax atleast on");
        }
        Tax tax = taxService.getTaxById(salariesReqDto.getTaxId());
        salaries.setTax(tax);
        return mapper.salariesToSalariesResponseDto(salariesRepo.save(salaries));
    }
    @Transactional
    @Override
    public SalariesRepDto editSalary(SalariesReqDto salariesReqDto, Long id) {
        Salaries update = getSalary(id);
        update.setSalary(salariesReqDto.getSalary());
        update.setFromDate(salariesReqDto.getFromDate());
        update.setToDate(salariesReqDto.getToDate());
        if (salariesReqDto.getTaxId() != null) {
            Tax tax = taxService.getTaxById(salariesReqDto.getTaxId());
            update.setTax(tax);
        }
        return  mapper.salariesToSalariesResponseDto(salariesRepo.save(update));
    }

    @Override
    public SalariesRepDto getSalaryById(Long id) {
        Salaries salaries = getSalary(id);
        return mapper.salariesToSalariesResponseDto(salaries);
    }

    @Override
    public List<SalariesRepDto> getListSalary() {
        return mapper.salariesToSalariesResponseDtos(salariesRepo.findAll());
    }

    @Override
    public Salaries getSalary(Long id) {
        return salariesRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        "Salary with id: " + id + " could not be found"));
    }

    @Override
    public void deleteSalary(Long id) {
        Salaries salaries = getSalary(id);
        salariesRepo.delete(salaries);
    }
}
