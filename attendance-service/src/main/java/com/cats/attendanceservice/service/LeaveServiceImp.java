package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.LeaveDtoRep;
import com.cats.attendanceservice.dto.LeaveDtoReq;
import com.cats.attendanceservice.dto.mapper;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveServiceImp implements LeaveSerivce{
    private final LeaveRepo leaveRepo;
    private final LeaveBalanceService leaveBalanceService;
    private final LeaveTypeService leaveTypeService;
    private final AttendanceRepo attendanceRepo;
    private final FileService fileService;
    @Override
    public List<LeaveDtoRep> getListLeave() {
        return mapper.leaveToLeaveResponseDtos(leaveRepo.findAll());
    }

    @Override
    public List<LeaveDtoRep> getLeaveByEmId(Long EmId) {
        return mapper.leaveToLeaveResponseDtos(leaveRepo.findLeaveByEmpId(EmId));
    }

    @Override
    public List<LeaveDtoRep> getLeaveByEmIdAndOrderByDate(Long EmId) {
        return mapper.leaveToLeaveResponseDtos(leaveRepo.findLeaveByEmpIdAndOOrderByDate(EmId));
    }

    //For user create leave and save draft
    @Transactional
    @Override
    public LeaveDtoRep create(LeaveDtoReq leaveDtoReq,  MultipartFile file) throws IOException {
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
        leave.setRemark(leaveDtoReq.getRemark());
        leave.setDayOfLeave(leaveDtoReq.getDayOfLeave());
        leave.setCreatedAt(leaveDtoReq.getCreatedAt());
        leave.setTimeOfHaftDay(leaveDtoReq.getTimeOfHaftDay());
        if (file != null){
            fileService.store(file,leaveDtoReq.getEmpId(),2);
        }
        return mapper.leaveToLeaveResponseDto(leaveRepo.save(leave));
    }

    //When the user apply leave and send to management
    // after user can't edit leave and note remark on attendance
    // that user take leave on that date
    @Transactional
    @Override
    public LeaveDtoRep appleLeave(LeaveDtoReq leaveDtoRep, Long Id) {
        Leave apply = getLeaveById(Id);
        apply.setStatus(leaveDtoRep.getStatus());
        Attendance attendance = new Attendance();
        attendance.setEmId(attendance.getEmId());
        attendance.setDateIn(apply.getCreatedAt());
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
    public List<LeaveDtoRep> ApprovedByManger(List<Long> Id, LeaveDtoReq leaveDtoRep) {
        List<Leave> leaveList = new ArrayList<>();
        for (Long LeaveId : Id){
            Leave leave = getLeaveById(LeaveId);
            leave.setApprovedByManger(leaveDtoRep.getApprovedByManger());
            leaveList.add(leave);
        }
        return mapper.leaveToLeaveResponseDtos(leaveRepo.saveAll(leaveList));
    }
    //for Head of department to approved
    @Override
    public List<LeaveDtoRep> ApprovedByHead(List<Long> Id, LeaveDtoReq leaveDtoRep) {
        List<Leave> leaveList = new ArrayList<>();
        for (Long LeaveId : Id){
            Leave leave = getLeaveById(LeaveId);
            leave.setApprovedByManger(leaveDtoRep.getApprovedByManger());
            leave.setApprovedByHead(leaveDtoRep.getApprovedByHead());
            leaveList.add(leave);
        }
        return mapper.leaveToLeaveResponseDtos(leaveRepo.saveAll(leaveList));
    }
    //This one for hr Approved and hr have permission for Approved all
    @Override
    public List<LeaveDtoRep> ApprovedByHr(List<Long> Id, LeaveDtoReq leaveDtoRep) {
        List<Leave> leaveList = new ArrayList<>();
        for (Long LeaveId : Id){
            Leave leave = getLeaveById(LeaveId);
            leave.setApprovedByManger(leaveDtoRep.getApprovedByManger());
            leave.setApprovedByHead(leaveDtoRep.getApprovedByHead());
            leave.setApprovedByHr(leaveDtoRep.getApprovedByHr());
            leave.setApproved(leaveDtoRep.getApproved());
            leaveList.add(leave);
            leaveBalanceService.editLeaveBalance(leave.getLeaveType().getId(), leave.getEmpId(), Long.valueOf(leave.getDayOfLeave()));

        }
        return mapper.leaveToLeaveResponseDtos(leaveRepo.saveAll(leaveList));
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
    public void delete(Long Id) {
        Leave leave = getLeaveById(Id);
        if(!leave.getStatus()){
            leaveRepo.delete(leave);
        }
        throw new IllegalArgumentException("You can not delete leave!");
    }
}
