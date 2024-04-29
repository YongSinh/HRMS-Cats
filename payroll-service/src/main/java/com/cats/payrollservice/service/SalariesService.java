package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.SalariesReqDto;
import com.cats.payrollservice.dto.response.SalariesRepDto;
import com.cats.payrollservice.model.Salaries;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalariesService {

    SalariesRepDto addSalary(SalariesReqDto salariesReqDto);
    SalariesRepDto editSalary(SalariesReqDto salariesReqDto, Long id);
    SalariesRepDto getSalaryById(Long id);
    List<SalariesRepDto> getListSalary();
    Salaries getSalary(Long id);
    void deleteSalary(Long id);

}
