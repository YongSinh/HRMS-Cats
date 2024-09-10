package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.SalariesReqDto;
import com.cats.payrollservice.dto.response.SalariesRepDto;
import com.cats.payrollservice.model.Salaries;

import java.util.List;


public interface SalariesService {

    SalariesRepDto addSalary(SalariesReqDto salariesReqDto);
    List<SalariesRepDto> addSalaryList(SalariesReqDto salariesReqDto, List<Long> emId);
    SalariesRepDto editSalary(SalariesReqDto salariesReqDto, Long id);
    SalariesRepDto getSalaryById(Long id);
    List<SalariesRepDto> getListSalary();
    List<SalariesRepDto> getListSalaryDepId(Long depId);
    Salaries getSalary(Long id);
    SalariesRepDto getSalaryByEmId(Long emId);
    void deleteSalary(Long id);

}
