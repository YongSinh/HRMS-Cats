package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.mapper;
import com.cats.payrollservice.dto.request.SalariesReqDto;
import com.cats.payrollservice.dto.response.SalariesRepDto;
import com.cats.payrollservice.model.Salaries;
import com.cats.payrollservice.model.Tax;
import com.cats.payrollservice.repository.SalariesRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
@RequiredArgsConstructor
public class SalariesServiceImp implements SalariesService {
    private final SalariesRepo salariesRepo;
    private final TaxService taxService;
    private final  ApiService apiService;

    @Transactional
    @Override
    public SalariesRepDto addSalary(SalariesReqDto salariesReqDto) {
        Salaries exists = salariesRepo.findByEmpId(
                salariesReqDto.getEmpId()
        );
        if (exists != null) {
            throw new IllegalArgumentException("Salary for the given employee in this already exists.");
        }
        Salaries salaries = new Salaries();
        salaries.setSalary(salariesReqDto.getSalary());
        salaries.setFromDate(salariesReqDto.getFromDate());
        salaries.setToDate(salariesReqDto.getToDate());
        salaries.setEmpId(salariesReqDto.getEmpId());
        if (salariesReqDto.getTaxId() == null) {
            throw new IllegalArgumentException("Tax atleast on");
        }
        Tax tax = taxService.getTaxById(salariesReqDto.getTaxId());
        salaries.setTax(tax);
        return mapper.salariesToSalariesResponseDto(salariesRepo.save(salaries));
    }

    @Override
    public List<SalariesRepDto> addSalaryList(SalariesReqDto salariesReqDto, List<Long> emId) {
        List<Salaries> salariesList = new ArrayList<>();
        for (Long emIds : emId){
            Salaries salaries = new Salaries();
            salaries.setSalary(salariesReqDto.getSalary());
            salaries.setFromDate(salariesReqDto.getFromDate());
            salaries.setToDate(salariesReqDto.getToDate());
            salaries.setEmpId(emIds);
            if (salariesReqDto.getTaxId() == null) {
                throw new IllegalArgumentException("Tax atleast on");
            }
            Tax tax = taxService.getTaxById(salariesReqDto.getTaxId());
            salaries.setTax(tax);
            salariesList.add(salaries);
        }
        salariesRepo.saveAll(salariesList);
        return mapper.salariesToSalariesResponseDtos(salariesList);
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
    public List<SalariesRepDto> getListSalaryDepId(Long depId) {
        Collection<Long> emIds = apiService.getEmployeeByDepId(depId);
        System.out.println(emIds.isEmpty());
        if (emIds.isEmpty()){
            return null;
        }
        return mapper.salariesToSalariesResponseDtos(salariesRepo.findByEmpIdIn(emIds));
    }

    @Override
    public Salaries getSalary(Long id) {
        return salariesRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        "Salary with id: " + id + " could not be found"));
    }

    @Override
    public SalariesRepDto getSalaryByEmId(Long emId) {
        Salaries salaries = salariesRepo.findByEmpId(emId);

        if (salaries == null) {
            throw new IllegalArgumentException("Salary details not found for employee ID: " + emId);
        }

        return mapper.salariesToSalariesResponseDto(salaries);
    }

    @Override
    public void deleteSalary(Long id) {
        Salaries salaries = getSalary(id);
        salariesRepo.delete(salaries);
    }
}
