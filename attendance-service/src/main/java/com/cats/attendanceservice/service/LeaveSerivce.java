package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.LeaveDtoRep;
import com.cats.attendanceservice.dto.LeaveDtoReq;
import com.cats.attendanceservice.model.Leave;

import java.util.List;

public interface LeaveSerivce {

    List<Leave> getListLeave();
    List<Leave> getLeaveByEmId(Long EmId);
    LeaveDtoRep create(LeaveDtoReq leaveDtoRep);
    LeaveDtoRep edit(Long Id, LeaveDtoReq leaveDtoRep);

    List<LeaveDtoRep> ApprovedByManger(List<Long> Id, LeaveDtoReq leaveDtoRep);
    List<LeaveDtoRep> ApprovedByHead(List<Long> Id, LeaveDtoReq leaveDtoRep);
    List<LeaveDtoRep> ApprovedByHr(List<Long> Id, LeaveDtoReq leaveDtoRep);

    Leave getLeaveById(Long id);

    void delete(Long Id);
}
