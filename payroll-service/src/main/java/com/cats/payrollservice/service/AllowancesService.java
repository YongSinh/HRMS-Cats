package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.AllowancesReqDto;
import com.cats.payrollservice.model.Allowances;

import java.util.List;


public interface AllowancesService {

    Allowances create(AllowancesReqDto allowancesReqDto);

    Allowances update(AllowancesReqDto allowancesReqDto, Long Id);

    void delete(Long id);

    Allowances getAllowancesBytId(Long id);

    List<Allowances> getListAllowances();
}
