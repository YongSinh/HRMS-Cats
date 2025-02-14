package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.DeductionsReqDto;
import com.cats.payrollservice.model.Deductions;

import java.util.List;


public interface DeductionsService {
    Deductions create(DeductionsReqDto deductionsReqDto);

    Deductions update(DeductionsReqDto deductionsReqDto, Long Id);

    Deductions getDeductionsById(Long Id);

    List<Deductions> getListDeduction();

    void delete(Long Id);
}
