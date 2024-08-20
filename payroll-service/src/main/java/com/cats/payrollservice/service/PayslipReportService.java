package com.cats.payrollservice.service;

import com.cats.payrollservice.non_entity_POJO.PayrollAndPaySlip;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface PayslipReportService {
    byte[] getPayslipForEmp(String refNo) throws IOException, JRException;
    List<PayrollAndPaySlip> getListPayslipForEmp(Long emId);
    List<PayrollAndPaySlip> getListPaySlip();
}
