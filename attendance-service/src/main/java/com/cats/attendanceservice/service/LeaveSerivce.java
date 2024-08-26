package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.LeaveApplyDtoReq;
import com.cats.attendanceservice.dto.LeaveApproveDtoReq;
import com.cats.attendanceservice.dto.LeaveDtoRep;
import com.cats.attendanceservice.dto.LeaveDtoReq;
import com.cats.attendanceservice.model.Leave;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface LeaveSerivce {

    List<LeaveDtoRep> getListLeave();
    List<LeaveDtoRep> getLeaveByEmId(Long EmId);
    List<LeaveDtoRep> getLeaveByEmIdAndOrderByDate(Long EmId);
    List<LeaveDtoRep> getLeaveByEmIdAndDate(LocalDate date, Long emId);
    List<LeaveDtoRep> getLeaveByDate(LocalDate date);
    List<LeaveDtoRep> getLeaveByDateBetween(LocalDate start, LocalDate endDate);
    List<LeaveDtoRep> getListByEmIdAndDateBetween(LocalDate startDate,LocalDate endDate, Long emId);
    LeaveDtoRep create(LeaveApplyDtoReq leaveDtoReq, MultipartFile file) throws IOException;
    LeaveDtoRep appleLeave(Long Id);
    LeaveDtoRep edit(Long Id, LeaveDtoReq leaveDtoRep);
    LeaveDtoRep editLeave(Long Id, LeaveDtoReq leaveDtoReq);
    List<LeaveDtoRep> ApprovedByManger(List<Long> Id, LeaveApproveDtoReq leaveDtoRep);
    List<LeaveDtoRep> ApprovedByHead(List<Long> Id, LeaveApproveDtoReq leaveDtoRep);
    List<LeaveDtoRep> ApprovedByHr(List<Long> Id, LeaveApproveDtoReq leaveDtoRep);

    List<LeaveDtoRep> getListLeaveForManger(Long emId);
    List<LeaveDtoRep> getListLeaveForHead(Long emId);

    Leave getLeaveById(Long id);

    LeaveDtoRep getLeave(Long id);
    void deleteForAdmin(Long Id);

    void delete(Long Id);
}
