package com.cats.payrollservice.service;

import com.cats.payrollservice.model.EmployeeDeductions;
import com.cats.payrollservice.non_entity_POJO.PaySlipReportDto;
import com.cats.payrollservice.non_entity_POJO.PayrollAndPaySlip;
import com.cats.payrollservice.repository.PayrollAndPayRepo;
import com.cats.payrollservice.repository.PayslipReprotRepo;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class PayslipReportServiceImp implements PayslipReportService{
    private final PayrollAndPayRepo payrollAndPayRepo;
    @Value(value = "${file.image-path-cats.logo}")
    private String catsLogo_path;
    @Value(value = "${file.report-path}")
    private String report_path;
    private final ApiService apiService;
    private final PayslipReprotRepo payslipReprotRepo;
    private final TaxService taxService;
    private final ServiceCalculate serviceCalculate;
    public JasperReport jasperReport (String filePath) throws JRException {
        return  JasperCompileManager.compileReport(filePath);
    }

    public JsonNode employeeInfo(Long emId) throws IOException {
        JsonNode employeeInfo = apiService.getEmployeeInFoByEmId(emId);
        System.out.println(employeeInfo);
        if (employeeInfo == null) {
            throw new IOException("Employee information not found for ID: " + emId);
        }
        return employeeInfo;
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
    @Transactional
    @Override
    public byte[] getPayslipForFirstAndSecondPayments(String refNo) throws IOException, JRException {
        List<PaySlipReportDto> payroll = payslipReprotRepo.getFirstAndSecondPayments(refNo);
        Map<String, List<PaySlipReportDto>> groupedPayroll = payroll.stream()
                .collect(Collectors.groupingBy(PaySlipReportDto::getPayment_sequence));
        List<PaySlipReportDto> firstPayments = groupedPayroll.getOrDefault("First Payment", List.of());
        List<PaySlipReportDto> secondPayments = groupedPayroll.getOrDefault("Second Payment", List.of());
        double tax = 0;
        if(!secondPayments.isEmpty()){
            Double khMoney = payroll.get(0).getSalary() * 4000D;
            tax = taxService.taxCalculator(khMoney) / 4000D;
        }
        JsonNode employeeInfo = apiService.getEmployeeInFoByEmId(payroll.get(0).getEmpId());
        if (employeeInfo == null) {
            throw new IOException("Employee information not found for ID: " +payroll.get(0).getEmpId());
        }
        double totalEarn = payroll.stream().mapToDouble(PaySlipReportDto::getTotal_earning).sum();
        String employeeName = employeeInfo.get("fullName").asText();
        String department = employeeInfo.get("department").asText();
        String section = employeeInfo.get("section").asText();
        String filePath = report_path+ "report/PaySlip2.jrxml";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("catsLogo", catsLogo_path);
        parameters.put("emId",payroll.get(0).getEmpId());
        parameters.put("username", employeeName);
        parameters.put("department", department);
        parameters.put("section",section);
        parameters.put("refNo",refNo);
        parameters.put("date",payroll.get(0).getDate());
        parameters.put("payPeriod",payroll.get(0).getType());
        parameters.put("tax_rate",payroll.get(0).getTax_rate());
        parameters.put("datasetFirst",beanCollectionDataSource(firstPayments));
        parameters.put("datasetSecond",beanCollectionDataSource(secondPayments));
        parameters.put("net",serviceCalculate.roundUp(totalEarn));
        parameters.put("tax",serviceCalculate.roundUp(tax));
        parameters.put("salary",payroll.get(0).getSalary());
        parameters.put("secondDataSetIsNull",secondPayments.isEmpty());

        // Handle each list
//        System.out.println("First Payments: " + firstPayments.size());
//        System.out.println("Second Payments: " + secondPayments.size());
//        payroll.forEach(System.out::println);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport(filePath),parameters, new JREmptyDataSource());
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    @Transactional
    @Override
    public byte[] getPayslipListReport(Long emId) throws IOException, JRException {
        String employeeName = employeeInfo(emId).get("fullName").asText();
        String department = employeeInfo(emId).get("department").asText();
        String section = employeeInfo(emId).get("section").asText();
        String filePath = report_path+ "report/payrollList.jrxml";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("catsLogo", catsLogo_path);
        parameters.put("emId",emId);
        parameters.put("username", employeeName);
        parameters.put("department", department);
        parameters.put("section",section);
        parameters.put("payslipDataset", beanCollectionDataSource(getListPaySlip()));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport(filePath),parameters, new JREmptyDataSource());
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
    @Transactional
    @Override
    public byte[] getPayslipListReportByDate(LocalDate date) throws IOException, JRException {
        return new byte[0];
    }
    @Transactional
    @Override
    public byte[] getPayslipReportByDate(LocalDate date, Long emId) throws IOException, JRException {

        String employeeName = employeeInfo(emId).get("fullName").asText();
        String department = employeeInfo(emId).get("department").asText();
        String section = employeeInfo(emId).get("section").asText();
        String filePath = report_path+ "report/payrollList.jrxml";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("catsLogo", catsLogo_path);
        parameters.put("emId",emId);
        parameters.put("username", employeeName);
        parameters.put("department", department);
        parameters.put("section",section);
        parameters.put("payslipDataset", beanCollectionDataSource(getPayrollByCreateDate(date)));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport(filePath),parameters, new JREmptyDataSource());
        return JasperExportManager.exportReportToPdf(jasperPrint);

    }

    @Transactional
    @Override
    public List<PayrollAndPaySlip> getListPayslipForEmp(Long emId) {
        return payrollAndPayRepo.GetPayrollWithTaxForUser(emId);
    }

    @Override
    public List<PayrollAndPaySlip> getPayrollByCreateDate(LocalDate date) {
        return payrollAndPayRepo.GetPayrollByCreateDate(date);
    }

    @Transactional
    @Override
    public List<PayrollAndPaySlip> getListPaySlip() {
        return payrollAndPayRepo.GetListPayroll();
    }


}
