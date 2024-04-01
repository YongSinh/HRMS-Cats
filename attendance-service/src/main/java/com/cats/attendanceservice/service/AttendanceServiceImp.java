package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.AttendanceReqDto;
import com.cats.attendanceservice.model.Attendance;
import com.cats.attendanceservice.repository.AttendanceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImp implements AttendanceService  {
    private final AttendanceRepo attendanceRepo;

    @Override
    public List<Attendance> getListAttendance() {
        return attendanceRepo.findAll();
    }

    @Override
    public List<Attendance> getListAttendanceByEmId(Long emId) {
        return  attendanceRepo.findByEmId(emId);
    }

    @Override
    public Attendance update(Long Id, AttendanceReqDto attendanceReqDto) {
        return null;
    }

    @Override
    public Attendance create(AttendanceReqDto attendanceReqDto) {
        return null;
    }

    @Override
    public Attendance getAttendanceById(Long id) {
        return attendanceRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        "attendance  with id: " + id + " could not be found"));
    }
}
