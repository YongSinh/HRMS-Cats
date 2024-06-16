package com.cats.attendanceservice.controller;

import com.cats.attendanceservice.base.BaseApi;
import com.cats.attendanceservice.dto.LeaveBalanceListDtoReq;
import com.cats.attendanceservice.model.Attendance;
import com.cats.attendanceservice.model.LeaveBalance;
import com.cats.attendanceservice.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/attendanceLeave")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;
    /**
     * listAttendance
     * ListAttendanceOrderByDate
     * listAttendanceByEmId
     * manualAsync TimeIn and TimeOut
     * */
    @GetMapping("/attendance/listAttendance")
    public BaseApi<?> listAttendance() {
        List<Attendance> getList = attendanceService.getListAttendance();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List All the Attendance")
                .timestamp(LocalDateTime.now())
                .data(getList)
                .build();
    }

    @GetMapping("/attendance/ListAttendanceOrderByDate")
    public BaseApi<?> ListAttendanceOrderByDate() {
        List<Attendance> getList = attendanceService.getListAttendanceOrderByDate();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List All the Attendance")
                .timestamp(LocalDateTime.now())
                .data(getList)
                .build();
    }
    @GetMapping("/attendance/listAttendanceByEmId/{emId}")
    public BaseApi<?> listAttendanceByEmId(@PathVariable Long emId) {
        List<Attendance> getList = attendanceService.getListAttendanceByEmId(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List All the Attendance")
                .timestamp(LocalDateTime.now())
                .data(getList)
                .build();
    }

    @GetMapping("/attendance/getListAttendanceForManger")
    public BaseApi<?> getListAttendanceForManger(@RequestParam Long emId) {
        List<Attendance> getList = attendanceService.getListAttendanceForManger(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List All the Attendance")
                .timestamp(LocalDateTime.now())
                .data(getList)
                .build();
    }

    @GetMapping("/attendance/listAttendanceByDepOrPos")
    public BaseApi<?> listAttendanceByDepOrPos(@RequestParam Collection<Long> emId) {
        List<Attendance> getList = attendanceService.getAttendanceByDepartmentOrPosition(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List All the Attendance")
                .timestamp(LocalDateTime.now())
                .data(getList)
                .build();
    }
    @GetMapping("/attendance/manualAsyncTimeIn")
    public BaseApi<?> manualAsyncTimeIn() {
        String manualAsyncTimeIn = attendanceService.manualAsyncTimeIn();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message(manualAsyncTimeIn)
                .timestamp(LocalDateTime.now())
                .data(manualAsyncTimeIn)
                .build();
    }

    @GetMapping("/attendance/manualAsyncTimeOut")
    public BaseApi<?> manualAsyncTimeOut() {
        String manualAsyncTimeOut = attendanceService.manualAsyncTimeOut();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message(manualAsyncTimeOut)
                .timestamp(LocalDateTime.now())
                .data(manualAsyncTimeOut)
                .build();
    }
}
