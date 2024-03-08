package com.cats.attendanceservice.service;

import com.cats.attendanceservice.repository.AttendanceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImp implements AttendanceService  {
    private final AttendanceRepo attendanceRepo;
}
