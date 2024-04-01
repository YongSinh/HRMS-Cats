package com.cats.attendanceservice.controller;

import com.cats.attendanceservice.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendanceLeave")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;


}
