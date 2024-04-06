package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.AttendanceReqDto;
import com.cats.attendanceservice.model.Attendance;
import com.cats.attendanceservice.repository.AttendanceRepo;

import java.util.List;

public interface AttendanceService {
    List<Attendance> getListAttendance();
    List<Attendance> getListAttendanceByEmId(Long emId);
    Attendance update(Long Id, AttendanceReqDto attendanceReqDto);
    Attendance create( AttendanceReqDto attendanceReqDto);
    Attendance getAttendanceById( Long id);
    void saveAttendanceToDb();

}
