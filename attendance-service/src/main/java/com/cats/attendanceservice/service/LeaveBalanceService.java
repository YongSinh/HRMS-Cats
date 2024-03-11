package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.LeaveBalanceDtoRep;
import com.cats.attendanceservice.dto.LeaveTypeReqDto;
import com.cats.attendanceservice.model.LeaveBalance;

import java.util.List;

public interface LeaveBalanceService {
    LeaveBalance create(LeaveTypeReqDto leaveTypeReqDto);
    LeaveBalance edit(LeaveTypeReqDto leaveTypeReqDto, Long Id);
    LeaveBalance getLeaveBalance(Long Id);
    LeaveBalanceDtoRep getLeaveBalanceById(Long Id);
    List<LeaveBalanceDtoRep> getListLeaveBalance();
}
