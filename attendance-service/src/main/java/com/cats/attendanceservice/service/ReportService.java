package com.cats.attendanceservice.service;

import net.sf.jasperreports.engine.JRException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public interface ReportService {
    byte[] getAttendanceReportByEmId(LocalDate date, LocalDate date2, Long emId) throws IOException, JRException;

}
