package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.LeaveDtoRep;
import com.cats.attendanceservice.dto.ReportAttendanceDto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ReportServiceImp implements ReportService {
    private final AttendanceService attendanceService;
    private final  LeaveSerivce leaveSerivce;
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

    @Override
    public byte[] getAttendanceReportByEmId(LocalDate date, LocalDate date2, Long emId) throws IOException, JRException {
        List<ReportAttendanceDto> attendances = attendanceService.getReportByDateInBetweenAndEmId(date,date2,emId);
        JsonNode employeeInfo = apiService.getEmployeeInFoByEmId(emId);
        if (employeeInfo == null) {
            throw new IOException("Employee information not found for ID: " + emId);
        }

        String employeeName = employeeInfo.get("fullName").asText();
        String department = employeeInfo.get("department").asText();
        String section = employeeInfo.get("section").asText();
        String filePath = report_path+ "report/Attendance.jrxml";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("catsLogo", catsLogo_path);
        parameters.put("attendanceDataset",beanCollectionDataSource(attendances));
        parameters.put("emId",emId);
        parameters.put("username", employeeName);
        parameters.put("startDate", date.toString());
        parameters.put("endDate", date2.toString());
        parameters.put("department", department);
        parameters.put("section",section);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport(filePath),parameters,new JREmptyDataSource());
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    @Override
    public byte[] getLeaveByEmIdAndDateBetween(LocalDate date, LocalDate date2, Long emId) throws IOException, JRException {
        return new byte[0];
    }

    @Override
    public byte[] getLeaveAndDateBetween(LocalDate date, LocalDate date2, Long emId) throws IOException, JRException {
        List<LeaveDtoRep> leaveDtoReps = leaveSerivce.getLeaveByDateBetween(date, date2);
        JsonNode employeeInfo = apiService.getEmployeeInFoByEmId(emId);
        if (employeeInfo == null) {
            throw new IOException("Employee information not found for ID: " + emId);
        }

        String employeeName = employeeInfo.get("fullName").asText();
        String department = employeeInfo.get("department").asText();
        String section = employeeInfo.get("section").asText();
        String filePath = report_path+ "report/Leave.jrxml";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("catsLogo", catsLogo_path);
        parameters.put("attendanceDataset",beanCollectionDataSource(leaveDtoReps));
        parameters.put("emId",emId);
        parameters.put("username", employeeName);
        parameters.put("startDate", date.toString());
        parameters.put("endDate", date2.toString());
        parameters.put("department", department);
        parameters.put("section",section);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport(filePath),parameters,new JREmptyDataSource());
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
