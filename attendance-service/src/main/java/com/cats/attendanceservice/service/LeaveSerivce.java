package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.LeaveApplyDtoReq;
import com.cats.attendanceservice.dto.LeaveDtoRep;
import com.cats.attendanceservice.dto.LeaveDtoReq;
import com.cats.attendanceservice.dto.LeaveDtoReqEdit;
import com.cats.attendanceservice.model.Leave;
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

    List<LeaveDtoRep> getListByEmIdAndDateBetween(LocalDate startDate, LocalDate endDate, Long emId);

    LeaveDtoRep create(LeaveApplyDtoReq leaveDtoReq, MultipartFile file) throws IOException;

    LeaveDtoRep appleLeave(Long Id);

    LeaveDtoRep edit(Long Id, LeaveDtoReq leaveDtoRep);

    LeaveDtoRep editLeave(LeaveDtoReqEdit leaveIdRep, MultipartFile file) throws IOException;

    LeaveDtoRep ApprovedByManger(Long Id);

    LeaveDtoRep ApprovedByHead(Long Id);

    LeaveDtoRep ApprovedByHr(Long Id);

    LeaveDtoRep cancelLeave(Long Id);

    LeaveDtoRep rejectByManger(Long Id);

    LeaveDtoRep rejectByHead(Long Id);

    LeaveDtoRep rejectByHr(Long Id);

    List<LeaveDtoRep> getListLeaveForMangement(Long emId);

    Leave getLeaveById(Long id);

    LeaveDtoRep getLeave(Long id);

    void deleteForAdmin(Long Id);

    void delete(Long Id);

    boolean isLeaveAlreadyApplied(Long empId, LocalDate startDate, LocalDate endDate);
}
