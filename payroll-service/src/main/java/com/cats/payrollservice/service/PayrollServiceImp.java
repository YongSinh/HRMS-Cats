package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayrollReqDto;
import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.repository.PayrollRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class PayrollServiceImp implements PayrollService {

    private final PayrollRepo payrollRepo;
    @Override
    public Payroll getPayrollById(Long id) {
        return payrollRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        "Payroll with id: " + id + " could not be found"));
    }

    @Override
    public Payroll update(Long id, PayrollReqDto payrollReqDto) {
        Payroll payroll = getPayrollById(id);
        payroll.setEmpId(payrollReqDto.getEmpId());
        payroll.setRefNo(payrollReqDto.getRefNo());
        payroll.setDateFrom(payrollReqDto.getDateFrom());
        payroll.setDateTo(payrollReqDto.getDateTo());
        payroll.setDateCreate(payrollReqDto.getDateCreate());
        payroll.setType(payrollReqDto.getType());
        payroll.setStatus(payrollReqDto.getStatus());
        payrollRepo.save(payroll);
        return payroll;
    }

    @Override
    public Payroll create(PayrollReqDto payrollReqDto) {
        Payroll payroll = new Payroll();
        payroll.setEmpId(payrollReqDto.getEmpId());
        payroll.setRefNo(payrollReqDto.getRefNo());
        payroll.setDateFrom(payrollReqDto.getDateFrom());
        payroll.setDateTo(payrollReqDto.getDateTo());
        payroll.setDateCreate(payrollReqDto.getDateCreate());
        payroll.setType(payrollReqDto.getType());
        payroll.setStatus(payrollReqDto.getStatus());
        payrollRepo.save(payroll);
        return payroll;
    }
}
