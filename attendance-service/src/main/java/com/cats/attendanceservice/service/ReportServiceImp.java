package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.ReportAttendanceDto;
import com.cats.attendanceservice.model.Attendance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportServiceImp implements ReportService {
    private final AttendanceService attendanceService;

    @Override
    public ByteArrayOutputStream getAttendanceReportByEmId(LocalDate date, LocalDate date2, Long emId) throws IOException {
        List<ReportAttendanceDto> attendances = attendanceService.getReportByDateInBetweenAndEmId(date,date2,emId);
        return null;
    }
}
