package com.cats.attendanceservice.controller;

import com.cats.attendanceservice.base.BaseApi;
import com.cats.attendanceservice.dto.LeaveTypeReqDto;
import com.cats.attendanceservice.model.LeaveType;
import com.cats.attendanceservice.service.LeaveBalanceService;
import com.cats.attendanceservice.service.LeaveTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/attendanceLeave")
@RequiredArgsConstructor
public class LeaveTypeController {
    private final LeaveTypeService leaveTypeService;
    private final LeaveBalanceService leaveBalanceService;
    @PostMapping("/addLeaveType")
    public BaseApi<?> addLeaveType(@RequestBody LeaveTypeReqDto leaveTypeReqDto) {
        LeaveType leaveType = leaveTypeService.create(leaveTypeReqDto);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("leave type types have been create")
                .timestamp(LocalDateTime.now())
                .data(leaveType)
                .build();
    }

    @PostMapping("/editLeaveType/{Id}")
    public BaseApi<?> editLeaveType(@RequestBody LeaveTypeReqDto leaveTypeReqDto, @PathVariable String Id) {
        LeaveType leaveType = leaveTypeService.edit(leaveTypeReqDto,Id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("leave type types have been updated")
                .timestamp(LocalDateTime.now())
                .data(leaveType)
                .build();
    }
    @GetMapping("/getListLeaveType")
    public BaseApi<?> getListLeaveType( ) {
        List<LeaveType> leaveTypeList = leaveTypeService.getListLeave();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("leave type types have been found")
                .timestamp(LocalDateTime.now())
                .data(leaveTypeList)
                .build();
    }

    @GetMapping("/leaveTypeById/{Id}")
    public BaseApi<?> getLeaveTypeById(@PathVariable String Id ) {
       LeaveType leaveTypeList = leaveTypeService.getLeave(Id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("leave type types have been found")
                .timestamp(LocalDateTime.now())
                .data(leaveTypeList)
                .build();
    }

    @PostMapping("/updateLeaveBalance/{Id}/{newValue}")
    public BaseApi<?> updateLeaveBalance(@PathVariable String Id ,@PathVariable Long newValue) {
        leaveTypeService.updateLeaveBalance(Id, newValue);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("leave type types have been found")
                .timestamp(LocalDateTime.now())
                .data("leave type types have been found")
                .build();
    }

    // This controller will delete all the leave type with both tblLeave and tblLeaveBalance
    @Transactional
    @DeleteMapping("/deleteLeaveType/{Id}")
    public BaseApi<?> deleteLeaveType(@PathVariable String Id ) {
       // LeaveType leaveTypeList = leaveTypeService.getLeave(Id);
        leaveTypeService.delete(Id);
        leaveBalanceService.deleteByLeaveType(Id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("leave type types have been found")
                .timestamp(LocalDateTime.now())
                .data("")
                .build();
    }
}
