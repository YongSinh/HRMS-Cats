package com.cats.attendanceservice.controller;

import com.cats.attendanceservice.base.BaseApi;
import com.cats.attendanceservice.dto.LeaveBalanceDtoRep;
import com.cats.attendanceservice.dto.LeaveBalanceListDtoReq;
import com.cats.attendanceservice.dto.LeaveBalanceRepDto;
import com.cats.attendanceservice.model.LeaveBalance;
import com.cats.attendanceservice.service.LeaveBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/attendanceLeave")
@RequiredArgsConstructor
public class LeaveBalanceController {
    private final LeaveBalanceService leaveBalanceService;

    @PostMapping("/LeaveBalance/add")
    public BaseApi<?> addLeaveBalance(@RequestBody LeaveBalanceListDtoReq leaveBalanceListDtoReq) {
        List<LeaveBalance> leaveBalance = leaveBalanceService.create(leaveBalanceListDtoReq);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("leave types have been assign to the user")
                .timestamp(LocalDateTime.now())
                .data(leaveBalance)
                .build();
    }

    @PutMapping("/LeaveBalance/update/{Id}")
    public BaseApi<?> updateLeaveBalance(@RequestBody LeaveBalanceRepDto leaveBalanceRepDto, @PathVariable Long Id) {
        LeaveBalance leaveBalance = leaveBalanceService.edit(leaveBalanceRepDto, Id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User leave Balance have been updated")
                .timestamp(LocalDateTime.now())
                .data(leaveBalance)
                .build();
    }


    @GetMapping("/leaveBalance")
    public BaseApi<?> getLeaveBalance() {
        List<LeaveBalanceDtoRep> leaveBalanceList = leaveBalanceService.getListLeaveBalance();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("leave Balance have been found")
                .timestamp(LocalDateTime.now())
                .data(leaveBalanceList)
                .build();
    }

    @GetMapping("/getLeaveBalanceByEmIdAndType")
    public BaseApi<?> getLeaveBalanceByEmIdAndType(@RequestParam Long emId, @RequestParam String type) {
        LeaveBalanceDtoRep leaveBalanceList = leaveBalanceService.getLeaveBalanceByLeaveTypeAndEmpId(type, emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("leave Balance have been found")
                .timestamp(LocalDateTime.now())
                .data(leaveBalanceList)
                .build();
    }

    @GetMapping("/getLeaveBalanceByEmId")
    public BaseApi<?> getLeaveBalanceByEmId(@RequestParam Long emId) {
        List<LeaveBalanceDtoRep> leaveBalanceList = leaveBalanceService.getLeaveBalanceByEmId(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("leave Balance have been found")
                .timestamp(LocalDateTime.now())
                .data(leaveBalanceList)
                .build();
    }

    @GetMapping("/getLeaveBalanceById")
    public BaseApi<?> getLeaveBalanceById(@RequestParam Long id) {
        LeaveBalanceDtoRep leaveBalanceList = leaveBalanceService.getLeaveBalanceById(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("leave Balance have been found")
                .timestamp(LocalDateTime.now())
                .data(leaveBalanceList)
                .build();
    }

    @DeleteMapping("/LeaveBalance/delete/{Id}")
    public BaseApi<?> deleteLeaveBalance(@PathVariable Long Id) {
        leaveBalanceService.delete(Id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("leave have been remove from the user")
                .timestamp(LocalDateTime.now())
                .data("leave have been remove from the user")
                .build();
    }

}
