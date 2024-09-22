package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.*;
import com.cats.attendanceservice.model.Attendance;
import com.cats.attendanceservice.model.Leave;
import com.cats.attendanceservice.model.LeaveType;
import com.cats.attendanceservice.repository.AttendanceRepo;
import com.cats.attendanceservice.repository.LeaveRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveServiceImp implements LeaveSerivce{
    private final LeaveRepo leaveRepo;
    private final LeaveBalanceService leaveBalanceService;
    private final LeaveTypeService leaveTypeService;
    private final AttendanceRepo attendanceRepo;
    private final FileService fileService;
    private final ApiService apiService;
    @Override
    public List<LeaveDtoRep> getListLeave() {
        return mapper.leaveToLeaveResponseDtos(leaveRepo.findAllByOrderByCreatedAt());
    }

    @Override
    public List<LeaveDtoRep> getLeaveByEmId(Long EmId) {
        return mapper.leaveToLeaveResponseDtos(leaveRepo.findLeaveByEmpIdOrderByCreatedAtDesc(EmId));
    }

    @Override
    public List<LeaveDtoRep> getLeaveByEmIdAndOrderByDate(Long EmId) {
        return mapper.leaveToLeaveResponseDtos(leaveRepo.findLeaveByEmpIdAndOOrderByDate(EmId));
    }

    @Override
    public List<LeaveDtoRep> getLeaveByEmIdAndDate(LocalDate date, Long emId) {
        return mapper.leaveToLeaveResponseDtos(leaveRepo.findByEmpIdAndCreatedAtOrderByCreatedAtDesc(emId,date));
    }

    @Override
    public List<LeaveDtoRep> getLeaveByDate(LocalDate date) {
        return mapper.leaveToLeaveResponseDtos(leaveRepo.findByCreatedAtOrderByCreatedAtDesc(date));
    }

    @Override
    public List<LeaveDtoRep> getLeaveByDateBetween(LocalDate start, LocalDate endDate) {
        return mapper.leaveToLeaveResponseDtos(leaveRepo.findByCreatedAtBetween(start, endDate));
    }

    @Override
    public List<LeaveDtoRep> getListByEmIdAndDateBetween(LocalDate startDate, LocalDate endDate, Long emId) {
        return mapper.leaveToLeaveResponseDtos(leaveRepo.findByEmpIdAndCreatedAtBetween(emId, startDate, endDate));
    }


    //For user create leave and save draft
    @Transactional
    @Override
    public LeaveDtoRep create(LeaveApplyDtoReq leaveDtoReq, MultipartFile file) throws IOException {
        Leave leave = new Leave();
        leave.setEmpId(leaveDtoReq.getEmpId());
        leave.setStartDate(leaveDtoReq.getStartDate());
        leave.setEndDate(leaveDtoReq.getEndDate());
        if(leaveDtoReq.getLeaveTypeId() == null){
            throw new IllegalArgumentException("Please select the leave type");
        }
        LeaveType leaveType = leaveTypeService.getLeave(leaveDtoReq.getLeaveTypeId());
        leave.setLeaveType(leaveType);
        leave.setStatus(false);
        leave.setApproved(false);
        leave.setCancelled(false);
        leave.setApprovedByManger(false);
        leave.setApprovedByHead(false);
        leave.setApprovedByHr(false);
        leave.setReason(leaveDtoReq.getReason());
        leave.setRemark(leaveDtoReq.getRemark());
        leave.setDayOfLeave(leaveDtoReq.getDayOfLeave());
        leave.setCreatedAt(leaveDtoReq.getCreatedAt());
        leave.setTimeOfHaftDay(leaveDtoReq.getTimeOfHaftDay());
        if (file != null){
            fileService.store(file,leaveDtoReq.getEmpId(),2, leaveDtoReq.getCreatedAt(), 2);
        }
        return mapper.leaveToLeaveResponseDto(leaveRepo.save(leave));
    }

    //When the user apply leave and send to management
    // after user can't edit leave and note remark on attendance
    // that user take leave on that date
    @Transactional
    @Override
    public LeaveDtoRep appleLeave(Long Id) {
        Leave apply = getLeaveById(Id);
        apply.setStatus(true);
        Attendance attendance = new Attendance();
        attendance.setEmId(apply.getEmpId());
        attendance.setDateIn(apply.getCreatedAt());
        attendance.setDateOut(apply.getCreatedAt());
        attendance.setRemark(apply.getReason());
        attendanceRepo.save(attendance);
        return mapper.leaveToLeaveResponseDto(leaveRepo.save(apply));
    }

    // for admin
    @Override
    public LeaveDtoRep edit(Long Id, LeaveDtoReq leaveDtoReq) {
        Leave leave = getLeaveById(Id);
        leave.setStartDate(leaveDtoReq.getStartDate());
        leave.setEndDate(leaveDtoReq.getEndDate());
        if(leaveDtoReq.getLeaveTypeId() != null){
            LeaveType leaveType = leaveTypeService.getLeave(leaveDtoReq.getLeaveTypeId());
            leave.setLeaveType(leaveType);
        }
        leave.setStatus(leaveDtoReq.getStatus());
        leave.setReason(leave.getReason());
        leave.setApproved(leaveDtoReq.getApproved());
        leave.setCancelled(leaveDtoReq.getCancelled());
        leave.setApprovedByManger(leaveDtoReq.getApprovedByManger());
        leave.setApprovedByHead(leaveDtoReq.getApprovedByHead());
        leave.setApprovedByHr(leaveDtoReq.getApprovedByHr());
        leave.setRemark(leaveDtoReq.getRemark());
        leave.setDayOfLeave(leaveDtoReq.getDayOfLeave());
        leave.setCreatedAt(leaveDtoReq.getCreatedAt());
        leave.setTimeOfHaftDay(leaveDtoReq.getTimeOfHaftDay());
        if(leave.getApprovedByHr()){
            leaveBalanceService.editLeaveBalance(leaveDtoReq.getLeaveTypeId(), leaveDtoReq.getEmpId(), Long.valueOf(leaveDtoReq.getDayOfLeave()));
        }
        return mapper.leaveToLeaveResponseDto(leaveRepo.save(leave));
    }

    //user
    @Override
    public LeaveDtoRep editLeave(Long Id, LeaveDtoReq leaveDtoReq) {
        Leave leave = getLeaveById(Id);
        leave.setStartDate(leaveDtoReq.getStartDate());
        leave.setEndDate(leaveDtoReq.getEndDate());
        if(leaveDtoReq.getLeaveTypeId() != null){
            LeaveType leaveType = leaveTypeService.getLeave(leaveDtoReq.getLeaveTypeId());
            leave.setLeaveType(leaveType);
        }
        leave.setStatus(leaveDtoReq.getStatus());
        leave.setRemark(leaveDtoReq.getRemark());
        leave.setDayOfLeave(leaveDtoReq.getDayOfLeave());
        leave.setCreatedAt(leaveDtoReq.getCreatedAt());
        leave.setTimeOfHaftDay(leaveDtoReq.getTimeOfHaftDay());
        return mapper.leaveToLeaveResponseDto(leaveRepo.save(leave));
    }

    //for manger to approved
    @Override
    public LeaveDtoRep ApprovedByManger(Long Id) {
            Leave leave = getLeaveById(Id);
            leave.setApprovedByManger(true);
        return mapper.leaveToLeaveResponseDto(leaveRepo.save(leave));
    }
    //for Head of department to approved
    @Override
    public LeaveDtoRep ApprovedByHead(Long Id) {
            Leave leave = getLeaveById(Id);
            leave.setApprovedByManger(true);
            leave.setApprovedByHead(true);
        return mapper.leaveToLeaveResponseDto(leaveRepo.save(leave));
    }
    @Transactional
    @Override
    public LeaveDtoRep ApprovedByHr(Long Id) {
            Leave leave = getLeaveById(Id);
            leave.setApprovedByManger(true);
            leave.setApprovedByHead(true);
            leave.setApprovedByHr(true);
            leave.setApproved(true);
            leaveBalanceService.editLeaveBalance(leave.getLeaveType().getId(), leave.getEmpId(), Long.valueOf(leave.getDayOfLeave()));
        return mapper.leaveToLeaveResponseDto(leaveRepo.save(leave));
    }

    @Override
    public LeaveDtoRep rejectByManger(Long Id) {
        Leave leave = getLeaveById(Id);
        leave.setApprovedByManger(false);
        leave.setApproved(false);
        leave.setCancelled(true);
        return mapper.leaveToLeaveResponseDto(leaveRepo.save(leave));
    }

    @Override
    public LeaveDtoRep rejectByHead(Long Id) {
        Leave leave = getLeaveById(Id);
        leave.setApprovedByManger(false);
        leave.setApprovedByHead(false);
        leave.setApproved(false);
        leave.setCancelled(true);
        return mapper.leaveToLeaveResponseDto(leaveRepo.save(leave));
    }

    @Override
    public LeaveDtoRep rejectByHr(Long Id) {
        Leave leave = getLeaveById(Id);
        leave.setApprovedByHr(false);
        leave.setApproved(false);
        leave.setCancelled(true);
        return mapper.leaveToLeaveResponseDto(leaveRepo.save(leave));
    }

    @Override
    public List<LeaveDtoRep> getListLeaveForManger(Long emId) {
        Collection<Long> emIds = apiService.getEmployeeByUnderMangerOnlyEmId(emId);
        List<Leave> leaveList = leaveRepo.findByEmpIdIn(emIds);
        return mapper.leaveToLeaveResponseDtos(leaveList);
    }

    @Override
    public List<LeaveDtoRep> getListLeaveForHead(Long emId) {
        return null;
    }


    @Override
    public Leave getLeaveById(Long id) {
        return leaveRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        "leave  with id: " + id + " could not be found"));
    }

    @Override
    public LeaveDtoRep getLeave(Long id) {
        return mapper.leaveToLeaveResponseDto(getLeaveById(id));
    }

    @Override
    public void deleteForAdmin(Long Id) {
        Leave leave = getLeaveById(Id);
        leaveRepo.delete(leave);

    }

    @Override
    public void delete(Long Id) {
        Leave leave = getLeaveById(Id);
        if(!leave.getStatus()){
            leaveRepo.delete(leave);
        }
        throw new IllegalArgumentException("You can not delete leave!");
    }
}
