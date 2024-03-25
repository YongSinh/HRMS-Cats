package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.LeaveBalanceDtoRep;
import com.cats.attendanceservice.dto.LeaveBalanceListDtoReq;
import com.cats.attendanceservice.dto.LeaveBalanceRepDto;
import com.cats.attendanceservice.model.LeaveBalance;

import java.util.List;

public interface LeaveBalanceService {
    List<LeaveBalance> create(LeaveBalanceListDtoReq leaveBalanceListDtoReq);
    LeaveBalance  edit(LeaveBalanceRepDto leaveBalanceRepDto, Long Id);
    LeaveBalance getLeaveBalance(Long Id);
    LeaveBalanceDtoRep getLeaveBalanceById(Long Id);
    List<LeaveBalanceDtoRep> getLeaveBalanceByEmId(Long emId);
    List<LeaveBalanceDtoRep> getListLeaveBalance();
    void delete(Long Id);
    void deleteByLeaveType(String Id);
}
