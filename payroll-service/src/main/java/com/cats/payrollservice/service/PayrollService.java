package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayrollReqDto;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.non_entity_POJO.PayrollAndPaySlip;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


public interface PayrollService {
    Payroll getPayrollById(Long id);

    Payroll update(Long id, PayrollReqDto payrollReqDto);

    List<Payroll> create(PayrollReqDto payrollReqDto);

    List<Payroll> createAllEmp(PayrollReqDto payrollReqDto) throws IOException;

    Payroll getPayrollsForCurrentMonth(Long empIds);

    String generatePayrollReference();

    List<Payroll> getListPayroll();

    List<Payroll> getListPayrollByDesc();

    List<Payroll> getListPayrollByDate(LocalDate date);

    List<Payroll> getListPayRollByEmId(Long emId);

    Payroll getPayRollByEmIdAndCreateDate(Long emId, LocalDate date);

    Double calculateNetSalary(Long emId, Double KhRate);

    List<Payroll> findPayRollByDepEmId(Long depId);

    List<Payroll> getListPayrollByDateBetween(LocalDate start, LocalDate end);

    List<Payroll> getListPayrollByEmIdAndDateBetween(Long emId, LocalDate start, LocalDate end);

    void deletePayroll(Long id);

    void updateStatusByDate(LocalDate localDate);

    void existsByEmpIdAndDateCreateBetween(Long empId, LocalDate dateCreate, LocalDate dateCreate2);

    PayrollAndPaySlip getPayrollByRefNo2(String s);

}
