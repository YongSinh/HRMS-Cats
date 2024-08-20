package com.cats.payrollservice.service;

import com.cats.payrollservice.non_entity_POJO.PayrollAndPaySlip;
import com.cats.payrollservice.repository.PayrollAndPayRepo;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PayslipReportServiceImp implements PayslipReportService{
    private final PayrollAndPayRepo payrollAndPayRepo;
    @Value(value = "${file.image-path-cats.logo}")
    private String catsLogo_path;
    @Value(value = "${file.report-path}")
    private String report_path;
    private final ApiService apiService;

    public JasperReport jasperReport (String filePath) throws JRException {
        return  JasperCompileManager.compileReport(filePath);
    }

    private JRBeanCollectionDataSource beanCollectionDataSource (List<?> objects){
        return  new JRBeanCollectionDataSource(objects);
    }
    @Transactional
    @Override
    public byte[] getPayslipForEmp(String refNo) throws IOException, JRException {
        PayrollAndPaySlip paySlip = payrollAndPayRepo.GetPayrollByRefNo2(refNo);
        JsonNode employeeInfo = apiService.getEmployeeInFoByEmId(paySlip.getEmpId());
        if (employeeInfo == null) {
            throw new IOException("Employee information not found for ID: " + paySlip.getEmpId());
        }

        String employeeName = employeeInfo.get("fullName").asText();
        String department = employeeInfo.get("department").asText();
        String section = employeeInfo.get("section").asText();
        String filePath = report_path+ "report/PaySlip.jrxml";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("catsLogo", catsLogo_path);
        parameters.put("emId",paySlip.getEmpId());
        parameters.put("username", employeeName);
        parameters.put("department", department);
        parameters.put("section",section);
        parameters.put("refNo",refNo);
        parameters.put("date",paySlip.getDate().toString());
        parameters.put("payPeriod", paySlip.getType());
        parameters.put("tax_rate",paySlip.getTax_rate());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport(filePath),parameters, beanCollectionDataSource(Collections.singletonList(paySlip)));
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    @Override
    public List<PayrollAndPaySlip> getListPayslipForEmp(Long emId) {
        return payrollAndPayRepo.GetPayrollWithTaxForUser(emId);
    }

    @Override
    public List<PayrollAndPaySlip> getListPaySlip() {
        return payrollAndPayRepo.GetListPayroll();
    }
}
