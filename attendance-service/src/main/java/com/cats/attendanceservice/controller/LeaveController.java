package com.cats.attendanceservice.controller;

import com.cats.attendanceservice.base.BaseApi;
import com.cats.attendanceservice.dto.LeaveApplyDtoReq;
import com.cats.attendanceservice.dto.LeaveDtoRep;
import com.cats.attendanceservice.dto.LeaveDtoReq;
import com.cats.attendanceservice.dto.LeaveDtoReqEdit;
import com.cats.attendanceservice.model.Leave;
import com.cats.attendanceservice.service.LeaveSerivce;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/attendanceLeave")
@RequiredArgsConstructor
public class LeaveController {
    private final LeaveSerivce leaveSerivce;

    //for first create leave only
    @PostMapping(value = "/leave/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseApi<?> addLeave(@RequestPart("body") LeaveApplyDtoReq leaveDtoReq, @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        LeaveDtoRep leave = leaveSerivce.create(leaveDtoReq, file);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been create leave")
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }

    @GetMapping("/leave/check")
    public BaseApi<?> checkLeaveApplied(
            @RequestParam Long empId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        Boolean checkLeaveApplied = leaveSerivce.isLeaveAlreadyApplied(empId, start, end);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Leave has already been applied for the specified date range.")
                .timestamp(LocalDateTime.now())
                .data(checkLeaveApplied)
                .build();
    }

    @PutMapping("/leave/apply")
    public BaseApi<?> applyLeave(@RequestParam Long id) {
        LeaveDtoRep leave = leaveSerivce.appleLeave(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been apply leave")
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }

    @PutMapping(value = "/leave/edit", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseApi<?> editLeave(@RequestPart("body") LeaveDtoReqEdit leaveDtoReq, @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        LeaveDtoRep leave = leaveSerivce.editLeave(leaveDtoReq, file);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been edit leave")
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }

    @PutMapping("/leave/admin/editLeaveForEm")
    public BaseApi<?> editLeaveByAdmin(@RequestBody LeaveDtoReq leaveDtoReq, @RequestParam Long id) {
        LeaveDtoRep leave = leaveSerivce.edit(id, leaveDtoReq);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been edit leave")
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }

    @PutMapping("/leave/approveOrRejectByManger")
    public BaseApi<?> approveOrRejectByManger(@RequestParam Long id, @RequestParam Boolean reject) {
        LeaveDtoRep leave = reject ? leaveSerivce.rejectByManger(id) : leaveSerivce.ApprovedByManger(id);
        String action = reject ? "rejected" : "approved";
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message(String.format("You have %s the leave", action))
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }

    @PutMapping("/leave/approveOrRejectByHead")
    public BaseApi<?> approveOrRejectByHead(@RequestParam Long id, @RequestParam Boolean reject) {
        LeaveDtoRep leave = reject ? leaveSerivce.rejectByHead(id) : leaveSerivce.ApprovedByHead(id);
        String action = reject ? "rejected" : "approved";
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message(String.format("You have %s the leave", action))
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }


    @PutMapping("/leave/approveOrRejectByHr")
    public BaseApi<?> approveOrRejectByHr(@RequestParam Long id, @RequestParam Boolean reject) {
        LeaveDtoRep leave = reject ? leaveSerivce.rejectByHr(id) : leaveSerivce.ApprovedByHr(id);
        String action = reject ? "rejected" : "approved";
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message(String.format("You have %s the leave", action))
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }


    @GetMapping("/leave/getAll")
    public BaseApi<?> getAllLeave() {
        List<LeaveDtoRep> leave = leaveSerivce.getListLeave();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("all the staff leave")
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }

    @GetMapping("/leave/getLeaveByDateAndEmId")
    public BaseApi<?> getLeaveByDateAndEmId(@RequestParam(name = "emId") Long emId,
                                            @RequestParam(name = "date") LocalDate date
    ) {
        List<LeaveDtoRep> leaveDtoRep = leaveSerivce.getLeaveByEmIdAndDate(date, emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List all your leave!")
                .timestamp(LocalDateTime.now())
                .data(leaveDtoRep)
                .build();
    }

    @GetMapping("/leave/getLeaveByEmId/{emId}")
    public BaseApi<?> getLeaveByEmId(@PathVariable Long emId) {
        List<LeaveDtoRep> leaveDtoRep = leaveSerivce.getLeaveByEmId(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List all your leave!")
                .timestamp(LocalDateTime.now())
                .data(leaveDtoRep)
                .build();
    }

    @GetMapping("/leave/getLeaveByDateBetweenAndEmId")
    public BaseApi<?> getLeaveByDateBetweenAndEmId(@RequestParam(name = "emId") Long emId,
                                                   @RequestParam(name = "startDate") LocalDate startDate,
                                                   @RequestParam(name = "endDate") LocalDate endDate
    ) {
        List<LeaveDtoRep> leaveDtoRep = leaveSerivce.getListByEmIdAndDateBetween(startDate, endDate, emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List all your leave!")
                .timestamp(LocalDateTime.now())
                .data(leaveDtoRep)
                .build();
    }

    @GetMapping("/leave/getLeaveByDate")
    public BaseApi<?> getLeaveByDate(@RequestParam(name = "date") LocalDate date
    ) {
        List<LeaveDtoRep> leaveDtoRep = leaveSerivce.getLeaveByDate(date);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List all your leave!")
                .timestamp(LocalDateTime.now())
                .data(leaveDtoRep)
                .build();
    }

    @GetMapping("/leave/getLeaveByDateBetween")
    public BaseApi<?> getLeaveByDateBetween(@RequestParam(name = "startDate") LocalDate startDate,
                                            @RequestParam(name = "endDate") LocalDate endDate
    ) {
        List<LeaveDtoRep> leaveDtoRep = leaveSerivce.getLeaveByDateBetween(startDate, endDate);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List all your leave!")
                .timestamp(LocalDateTime.now())
                .data(leaveDtoRep)
                .build();
    }

    @GetMapping("/leave/getLeaveById")
    public BaseApi<?> getLeaveById(@RequestParam Long id) {
        LeaveDtoRep leave = leaveSerivce.getLeave(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("all the staff leave")
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }

    @PostMapping("/leave/cancelLeave")
    public BaseApi<?> cancelLeave(@RequestParam Long id) {
        LeaveDtoRep leave = leaveSerivce.cancelLeave(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been cancel leave!")
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }

    @GetMapping("/leave/getByEmIdOrderByDate")
    public BaseApi<?> getAllLeaveByEmId(@RequestParam Long emId) {
        List<LeaveDtoRep> leave = leaveSerivce.getLeaveByEmIdAndOrderByDate(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("leave by employee id " + emId + " have been found ")
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }

    @CircuitBreaker(name = "management", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "management")
    @Retry(name = "management")
    @GetMapping("/leave/getLeave/management")
    public CompletableFuture<BaseApi<?>> getListLeaveForManagement(@RequestParam Long emId) {
        return CompletableFuture.supplyAsync(() -> {
            //log.info("Fetching leave list for management with emId: {}", emId);
            List<LeaveDtoRep> leave = leaveSerivce.getListLeaveForMangement(emId);
            return BaseApi.builder()
                    .status(true)
                    .code(HttpStatus.OK.value())
                    .message("Leave list for manager has been found!")
                    .timestamp(LocalDateTime.now())
                    .data(leave)
                    .build();
        });
    }

    public CompletableFuture<BaseApi<?>> fallbackMethod(Long emId, Throwable throwable) {
        //log.error("Fallback triggered for getListLeaveForManagement with emId: {} due to: {}", emId, throwable.toString());
        return CompletableFuture.supplyAsync(() -> BaseApi.builder()
                .status(false)
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .message("Service is currently unavailable. Please try again later.")
                .timestamp(LocalDateTime.now())
                .build());
    }

    @GetMapping("/leave/delete")
    public BaseApi<?> getDeleteLeave(@RequestParam Long id) {
        Leave leave = leaveSerivce.getLeaveById(id);
        leaveSerivce.delete(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("leave have been delete by id " + id)
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }

    @GetMapping("/leave/admin/delete")
    public BaseApi<?> getDeleteLeaveByAdmin(@RequestParam Long id) {
        Leave leave = leaveSerivce.getLeaveById(id);
        leaveSerivce.deleteForAdmin(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("leave have been delete by id " + id)
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }

}
