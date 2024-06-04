package com.cats.attendanceservice.controller;

import com.cats.attendanceservice.base.BaseApi;
import com.cats.attendanceservice.dto.LeaveBalanceListDtoReq;
import com.cats.attendanceservice.dto.LeaveDtoRep;
import com.cats.attendanceservice.dto.LeaveDtoReq;
import com.cats.attendanceservice.model.Leave;
import com.cats.attendanceservice.model.LeaveBalance;
import com.cats.attendanceservice.service.LeaveSerivce;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/attendanceLeave")
@RequiredArgsConstructor
public class LeaveController {
    private final LeaveSerivce leaveSerivce;

    //for first create leave only
    @PostMapping("/leave/add")
    public BaseApi<?> addLeave(@RequestPart("body") LeaveDtoReq leaveDtoReq, @RequestPart("body")MultipartFile file) throws IOException {
        LeaveDtoRep leave = leaveSerivce.create(leaveDtoReq, file);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been create leave")
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }
    @PutMapping("/leave/apply")
    public BaseApi<?> applyLeave(@RequestBody LeaveDtoReq leaveDtoReq, @RequestParam Long id) {
        LeaveDtoRep leave = leaveSerivce.appleLeave(leaveDtoReq, id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been apply leave")
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }
    @PutMapping("/leave/user/editLeaveForEm")
    public BaseApi<?> editLeave(@RequestBody LeaveDtoReq leaveDtoReq, @RequestParam Long id) {
        LeaveDtoRep leave = leaveSerivce.editLeave(id, leaveDtoReq);
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
    @PutMapping("/leave/approvedByManger")
    public BaseApi<?> approvedByManger(@RequestPart(name = "body") LeaveDtoReq leaveDtoReq, @RequestPart(name = "id") List<Long> id) {
        List<LeaveDtoRep> leave = leaveSerivce.ApprovedByManger(id, leaveDtoReq);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been approved the leave")
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }

    @PutMapping("/leave/approvedByHead")
    public BaseApi<?> approvedByHead(@RequestPart(name = "body") LeaveDtoReq leaveDtoReq, @RequestPart(name = "id") List<Long> id) {
        List<LeaveDtoRep> leave = leaveSerivce.ApprovedByHead(id, leaveDtoReq);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been approved the leave")
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }
    @PutMapping("/leave/approvedByHr")
    public BaseApi<?> approvedByHr(@RequestPart(name = "body") LeaveDtoReq leaveDtoReq, @RequestPart(name = "id") List<Long> id) {
        List<LeaveDtoRep> leave = leaveSerivce.ApprovedByHr(id, leaveDtoReq);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been approved the leave")
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
    @GetMapping("/leave/getByEmIdOrderByDate")
    public BaseApi<?> getAllLeaveByEmId(@RequestParam Long emId) {
        List<LeaveDtoRep> leave = leaveSerivce.getLeaveByEmIdAndOrderByDate(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("leave by employee id "+ emId +" have been found ")
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }

    @GetMapping("/leave/delete")
    public BaseApi<?> getDeleteLeave(@RequestParam Long id) {
        Leave leave = leaveSerivce.getLeaveById(id);
        leaveSerivce.delete(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("leave have been delete by id "+ id )
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
                .message("leave have been delete by id "+ id )
                .timestamp(LocalDateTime.now())
                .data(leave)
                .build();
    }

}
