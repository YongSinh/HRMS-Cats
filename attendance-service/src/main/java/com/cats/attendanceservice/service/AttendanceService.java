package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.AttendanceReqDto;
import com.cats.attendanceservice.dto.ReportAttendanceDto;
import com.cats.attendanceservice.dto.TimeInReqDto;
import com.cats.attendanceservice.dto.TimeOutReqDto;
import com.cats.attendanceservice.model.Attendance;
import com.cats.attendanceservice.repository.AttendanceRepo;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface AttendanceService {
    List<Attendance> getListAttendance();
    List<Attendance> getListAttendanceOrderByDate();
    List<Attendance> getListAttendanceByEmId(Long emId);
    List<Attendance> findByDateInBetweenAndEmId(LocalDate dateIn, LocalDate dateIn2, Long emId);
    List<Attendance> getListAttendanceForManger(Long emId);
    Attendance update(Long Id, AttendanceReqDto attendanceReqDto);
    Attendance create( AttendanceReqDto attendanceReqDto);

    Attendance timeOut(Long Id, TimeOutReqDto attendanceReqDto);
    Attendance timeIn( TimeInReqDto attendanceReqDto);

    List<ReportAttendanceDto>  getReportByDateInBetweenAndEmId(LocalDate dateIn, LocalDate dateIn2, Long emId) throws IOException;

    Attendance getAttendanceById( Long id);
    Attendance getAttendanceByEmIdAndDateIn( LocalDate date, Long emId);
    String manualAsyncTimeIn();
    String manualAsyncTimeOut();
    void createWeekendAttendance() throws IOException;
    List<Attendance> getAttendanceByDepartmentOrPosition(Collection<Long> emId);

}
