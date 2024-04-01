package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.LeaveDtoRep;
import com.cats.attendanceservice.dto.LeaveDtoReq;
import com.cats.attendanceservice.dto.mapper;
import com.cats.attendanceservice.model.Leave;
import com.cats.attendanceservice.model.LeaveBalance;
import com.cats.attendanceservice.model.LeaveType;
import com.cats.attendanceservice.repository.LeaveRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveServiceImp implements LeaveSerivce{
    private final LeaveRepo leaveRepo;
    private final LeaveBalanceService leaveBalanceService;
    private final LeaveTypeService leaveTypeService;
    @Override
    public List<Leave> getListLeave() {
        return leaveRepo.findAll();
    }

    @Override
    public List<Leave> getLeaveByEmId(Long EmId) {
        return leaveRepo.findLeaveByEmpId(EmId);
    }

    @Transactional
    @Override
    public LeaveDtoRep create(LeaveDtoReq leaveDtoReq) {
        Leave leave = new Leave();
        leave.setEmpId(leaveDtoReq.getEmpId());
        leave.setStartDate(leaveDtoReq.getStartDate());
        leave.setEndDate(leaveDtoReq.getEndDate());
        if(leaveDtoReq.getLeaveTypeId() == null){
            throw new IllegalArgumentException("Please select the leave type");
        }
        LeaveType leaveType = leaveTypeService.getLeave(leaveDtoReq.getLeaveTypeId());
        leave.setLeaveType(leaveType);
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
        return mapper.leaveToLeaveResponseDto(leaveRepo.save(leave));
    }

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
    public List<LeaveDtoRep> ApprovedByManger(List<Long> Id, LeaveDtoReq leaveDtoRep) {
        List<Leave> leaveList = new ArrayList<>();
        for (Long LeaveId : Id){
            Leave leave = getLeaveById(LeaveId);
            leave.setApprovedByManger(leaveDtoRep.getApprovedByManger());
            leaveList.add(leave);
        }
        return mapper.leaveToLeaveResponseDtos(leaveRepo.saveAll(leaveList));
    }

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

    @Override
    public List<LeaveDtoRep> ApprovedByHr(List<Long> Id, LeaveDtoReq leaveDtoRep) {
        List<Leave> leaveList = new ArrayList<>();
        for (Long LeaveId : Id){
            Leave leave = getLeaveById(LeaveId);
            leave.setApprovedByManger(leaveDtoRep.getApprovedByManger());
            leave.setApprovedByHead(leaveDtoRep.getApprovedByHead());
            leave.setApprovedByHr(leaveDtoRep.getApprovedByHr());
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
    public void delete(Long Id) {
        Leave leave = getLeaveById(Id);
        if(!leave.getStatus()){
            leaveRepo.delete(leave);
        }
        throw new IllegalArgumentException("You can not delete leave!");
    }
}
