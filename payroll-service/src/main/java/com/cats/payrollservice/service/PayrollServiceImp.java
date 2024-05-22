package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.PayrollReqDto;
import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.dto.response.SalariesRepDto;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.model.Salaries;
import com.cats.payrollservice.model.Tax;
import com.cats.payrollservice.repository.PayrollRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PayrollServiceImp implements PayrollService {

    private final PayrollRepo payrollRepo;
    private final TaxService taxService;
    private final DeductionsService deductionsService;
    private final AllowancesService allowancesService;
    private final SalariesService salariesService;
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
        //payroll.setRefNo(payrollReqDto.getRefNo());
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
        String payrollReference = generatePayrollReference();
        payroll.setEmpId(payrollReqDto.getEmpId());
        payroll.setRefNo(payrollReference);
        payroll.setDateFrom(payrollReqDto.getDateFrom());
        payroll.setDateTo(payrollReqDto.getDateTo());
        payroll.setDateCreate(payrollReqDto.getDateCreate());
        payroll.setType(payrollReqDto.getType());
        payroll.setStatus(payrollReqDto.getStatus());
        payrollRepo.save(payroll);
        return null;
    }




    @Override
    public String generatePayrollReference() {
        // Format current date and time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String datePart = sdf.format(new Date());

        // Generate a random number
        Random random = new Random();
        int randomPart = random.nextInt(1000000); // 6-digit random number

        // Combine date and random parts
        return datePart + "-" + String.format("%06d", randomPart);
    }
}
