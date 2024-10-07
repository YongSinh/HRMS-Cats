package com.cats.attendanceservice.controller;

import com.cats.attendanceservice.base.BaseApi;
import com.cats.attendanceservice.dto.AttendanceReqDto;
import com.cats.attendanceservice.dto.TimeInReqDto;
import com.cats.attendanceservice.dto.TimeOutReqDto;
import com.cats.attendanceservice.model.Attendance;
import com.cats.attendanceservice.service.AttendanceService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/attendanceLeave")
@RequiredArgsConstructor
@Slf4j
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

    @PostMapping("/attendance/create")
    public BaseApi<?> createAttendance(@RequestBody AttendanceReqDto attendanceReqDto) {
        Attendance getList = attendanceService.create(attendanceReqDto);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Attendance have been created")
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

    @GetMapping("/attendance/listAttendanceDateInBetweenAndEmId")
    public BaseApi<?> listAttendanceDateInBetweenAndEmId(
            @RequestParam(name = "dateIn") LocalDate dateIn,
            @RequestParam(name = "dateIn2")  LocalDate dateIn2,
            @RequestParam(name = "emId")  Long emId) {
        List<Attendance> getList = attendanceService.findByDateInBetweenAndEmId(dateIn, dateIn2,emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List All the Attendance")
                .timestamp(LocalDateTime.now())
                .data(getList)
                .build();
    }

    @CircuitBreaker(name = "management", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "management")
    @Retry(name = "management")
    @GetMapping("/attendance/getListAttendanceForManger")
    public CompletableFuture<BaseApi<?>> getListAttendanceForManger(@RequestParam Long emId) {
        return CompletableFuture.supplyAsync(() -> {
            List<Attendance> getList = attendanceService.getListAttendanceForManger(emId);
            return BaseApi.builder()
                    .status(true)
                    .code(HttpStatus.OK.value())
                    .message("List All the Attendance")
                    .timestamp(LocalDateTime.now())
                    .data(getList)
                    .build();
        });
    }

    public CompletableFuture<BaseApi<?>> fallbackMethod(Long emId, Throwable throwable) {
        return CompletableFuture.supplyAsync(() -> BaseApi.builder()
                .status(false)
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .message("Service is currently unavailable. Please try again later.")
                .timestamp(LocalDateTime.now())
                .build());
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
    @PostMapping("/attendance/manualAsyncTimeIn")
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

    @PostMapping("/attendance/manualAsyncTimeOut")
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

    @PostMapping("/attendance/timeIn")
    public BaseApi<?> timeIn(@RequestBody TimeInReqDto attendanceReqDto) {
        Attendance timeIn = attendanceService.timeIn(attendanceReqDto);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have time in!")
                .timestamp(LocalDateTime.now())
                .data(timeIn)
                .build();
    }

    @PutMapping("/attendance/timeOut")
    public BaseApi<?> timeOut(@RequestBody TimeOutReqDto attendanceReqDto, @RequestParam Long id) {
        Attendance timeIn = attendanceService.timeOut(id, attendanceReqDto);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have time out!")
                .timestamp(LocalDateTime.now())
                .data(timeIn)
                .build();
    }
}
