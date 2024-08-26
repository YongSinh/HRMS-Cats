package com.cats.attendanceservice.dto;

import com.cats.attendanceservice.model.Attendance;
import com.cats.attendanceservice.model.Leave;
import com.cats.attendanceservice.model.LeaveBalance;
import com.cats.attendanceservice.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
public class mapper {
    private final ApiService apiService;
    public static LeaveBalanceDtoRep leaveBalanceToBookResponseDto(LeaveBalance leaveBalance) {
        LeaveBalanceDtoRep leaveBalanceDtoRep = new LeaveBalanceDtoRep();
        leaveBalanceDtoRep.setId(leaveBalance.getId());
        leaveBalanceDtoRep.setBalanceAmount(leaveBalance.getBalanceAmount());
        leaveBalanceDtoRep.setLeaveType(leaveBalance.getLeaveType().getLeaveTitle());
        leaveBalanceDtoRep.setEmpId(leaveBalance.getEmpId());
        leaveBalanceDtoRep.setLastUpdateDate(leaveBalance.getLastUpdateDate());

        return leaveBalanceDtoRep;
    }
    public static List<LeaveBalanceDtoRep> leaveBalanceToBookResponseDtos(List<LeaveBalance> leaveBalances) {
        List<LeaveBalanceDtoRep> leaveBalanceDtoRep = new ArrayList<>();

        for (LeaveBalance leaveBalance: leaveBalances) {
           leaveBalanceDtoRep.add(leaveBalanceToBookResponseDto(leaveBalance));
        }
        return leaveBalanceDtoRep;
    }

    public static LeaveDtoRep leaveToLeaveResponseDto(Leave leave){
        LeaveDtoRep leaveDtoRep = new LeaveDtoRep();
        leaveDtoRep.setId(leave.getLeaveId());
        leaveDtoRep.setEmpId(leave.getEmpId());
        leaveDtoRep.setStartDate(leave.getStartDate().toString());
        leaveDtoRep.setEndDate(leave.getEndDate().toString());
        leaveDtoRep.setLeaveType(leave.getLeaveType().getLeaveTitle());
        leaveDtoRep.setStatus(leave.getStatus());
        leaveDtoRep.setReason(leave.getReason());
        leaveDtoRep.setApproved(leave.getApproved());
        leaveDtoRep.setCancelled(leave.getCancelled());
        leaveDtoRep.setApprovedByManger(leave.getApprovedByManger());
        leaveDtoRep.setApprovedByHead(leave.getApprovedByHead());
        leaveDtoRep.setApprovedByHead(leave.getApprovedByHead());
        leaveDtoRep.setApprovedByHr(leave.getApprovedByHr());
        leaveDtoRep.setRemark(leave.getRemark());
        leaveDtoRep.setDayOfLeave(leave.getDayOfLeave());
        leaveDtoRep.setCreatedAt(leave.getCreatedAt());
        leaveDtoRep.setTimeOfHaftDay(leave.getTimeOfHaftDay());
        return leaveDtoRep;
    }
    public static List<LeaveDtoRep> leaveToLeaveResponseDtos(List<Leave> leaves) {
        List<LeaveDtoRep>  leaveDtoReps = new ArrayList<>();

        for (Leave leave: leaves) {
            leaveDtoReps.add(leaveToLeaveResponseDto(leave));
        }
        return leaveDtoReps;
    }

}
