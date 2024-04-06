package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.LeaveDtoRep;
import com.cats.attendanceservice.dto.LeaveDtoReq;
import com.cats.attendanceservice.model.Leave;

import java.util.List;

public interface LeaveSerivce {

    List<LeaveDtoRep> getListLeave();
    List<LeaveDtoRep> getLeaveByEmId(Long EmId);
    List<LeaveDtoRep> getLeaveByEmIdAndOrderByDate(Long EmId);
    LeaveDtoRep create(LeaveDtoReq leaveDtoReq);
    LeaveDtoRep appleLeave(LeaveDtoReq leaveDtoRep, Long Id);
    LeaveDtoRep edit(Long Id, LeaveDtoReq leaveDtoRep);
    LeaveDtoRep editLeave(Long Id, LeaveDtoReq leaveDtoReq);
    List<LeaveDtoRep> ApprovedByManger(List<Long> Id, LeaveDtoReq leaveDtoRep);
    List<LeaveDtoRep> ApprovedByHead(List<Long> Id, LeaveDtoReq leaveDtoRep);
    List<LeaveDtoRep> ApprovedByHr(List<Long> Id, LeaveDtoReq leaveDtoRep);

    Leave getLeaveById(Long id);

    LeaveDtoRep getLeave(Long id);



    void delete(Long Id);
}
