package com.cats.attendanceservice.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public interface ReportService {
    ByteArrayOutputStream getAttendanceReportByEmId(LocalDate date, LocalDate date2, Long emId) throws IOException;
}
