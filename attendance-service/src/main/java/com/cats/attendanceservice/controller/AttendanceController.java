package com.cats.attendanceservice.controller;

import com.cats.attendanceservice.base.BaseApi;
import com.cats.attendanceservice.model.Attendance;
import com.cats.attendanceservice.service.AttendanceService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @CircuitBreaker(name = "management",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "management")
    @Retry(name = "management")
    @GetMapping("/attendance/getListAttendanceForManger")
    public CompletableFuture<?> getListAttendanceForManger(@RequestParam Long emId) {
        List<Attendance> getList = attendanceService.getListAttendanceForManger(emId);
        return CompletableFuture.supplyAsync(() -> getList);
//        return BaseApi.builder()
//                .status(true)
//                .code(HttpStatus.OK.value())
//                .message("List All the Attendance")
//                .timestamp(LocalDateTime.now())
//                .data(getList)
//                .build();
    }
    public CompletableFuture<String> fallbackMethod(Long emId, RuntimeException runtimeException) {
        System.out.println("Cannot Place Order Executing Fallback logic");
        return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, please order after some time!");
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
