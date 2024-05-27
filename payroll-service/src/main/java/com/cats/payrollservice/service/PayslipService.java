package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.model.Payslip;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface PayslipService {

    List<Payslip> create(PayslipReqDto payslipReqDto, List<Long> emId);
    List<Payslip>  update(PayslipReqDto payslipReqDto, Long id, List<Long> emId);
    List<Payslip> getListPaySlip();
    Payslip getListPaySlipByeEmIdAndCreateDate(Long emId, LocalDateTime localDateTime);
    List<Payslip> getListPaySlipByEmId(Long emId);
    Payslip getPaySlipById(Long id);

    void delete(Long id);

}
