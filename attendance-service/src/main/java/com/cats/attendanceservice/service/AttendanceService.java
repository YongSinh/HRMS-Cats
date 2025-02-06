package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.*;
import com.cats.attendanceservice.model.Attendance;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface AttendanceService {
    List<AttendanceRepDto> getListAttendance();

    List<AttendanceRepDto> getListAttendanceOrderByDate();

    List<AttendanceRepDto> getListAttendanceByEmId(Long emId);

    List<AttendanceRepDto> findByDateInBetweenAndEmId(LocalDate dateIn, LocalDate dateIn2, Long emId);

    List<AttendanceRepDto> getListAttendanceForManger(Long emId);

    Attendance update(Long Id, AttendanceReqDto attendanceReqDto);

    Attendance create(AttendanceReqDto attendanceReqDto);

    Attendance timeOut(Long Id, TimeOutReqDto attendanceReqDto);

    Attendance timeIn(TimeInReqDto attendanceReqDto);

    List<ReportAttendanceDto> getReportByDateInBetweenAndEmId(LocalDate dateIn, LocalDate dateIn2, Long emId) throws IOException;

    Attendance getAttendanceById(Long id);

    Attendance getAttendanceByEmIdAndDateIn(LocalDate date, Long emId);

    String manualAsyncTimeIn();

    String manualAsyncTimeOut();

    void createWeekendAttendance() throws IOException;

    List<Attendance> getAttendanceByDepartmentOrPosition(Collection<Long> emId);

}
