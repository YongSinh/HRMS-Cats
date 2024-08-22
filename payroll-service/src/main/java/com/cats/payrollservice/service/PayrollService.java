package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayrollReqDto;
import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.non_entity_POJO.PayrollAndPaySlip;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


public interface PayrollService {
    Payroll getPayrollById(Long id);
    Payroll update(Long id, PayrollReqDto payrollReqDto);
    List<Payroll> create(PayrollReqDto payrollReqDto, List<Long> emIds);
    List<Payroll> createAllEmp(PayrollReqDto payrollReqDto) throws IOException;
    String generatePayrollReference();
    List<Payroll> getListPayroll();
    List<Payroll> getListPayRollByEmId(Long emId);
    Payroll getPayRollByEmIdAndCreateDate(Long emId, LocalDate date);
    Double calculateNetSalary(Long emId, Double KhRate);
    List<Payroll> findPayRollByDepEmId(Long depId);
    void deletePayroll(Long id);
    PayrollAndPaySlip getPayrollByRefNo2(String s);

}
