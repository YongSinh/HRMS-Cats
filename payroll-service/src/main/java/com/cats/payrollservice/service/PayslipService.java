package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.model.Payslip;

import java.util.List;


public interface PayslipService {

    Payslip create(PayslipReqDto payslipReqDto);
    Payslip update(PayslipReqDto payslipReqDto, Long id);
    List<Payslip> getListPaySlip();
    List<Payslip> getListPaySlipByEmId(Long emId);
    Payslip getPaySlipById(Long id);

    void delete(Long id);

}
