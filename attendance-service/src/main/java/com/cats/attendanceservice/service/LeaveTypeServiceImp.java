package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.LeaveTypeReqDto;
import com.cats.attendanceservice.model.LeaveType;
import com.cats.attendanceservice.repository.LeaveTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveTypeServiceImp implements LeaveTypeService{
    private final LeaveTypeRepo leaveTypeRepo;

    @Override
    public LeaveType create(LeaveTypeReqDto leaveTypeReqDto) {
        LeaveType leaveType1 = new LeaveType();
        leaveType1.setId(leaveTypeReqDto.getId());
        leaveType1.setLeaveDes(leaveTypeReqDto.getLeaveDes());
        leaveType1.setLeaveTitle(leaveTypeReqDto.getLeaveTitle());
        leaveType1.setLeaveDayPerYear(leaveTypeReqDto.getLeaveDayPerYear());
        return leaveTypeRepo.save(leaveType1);
    }

    @Override
    public LeaveType edit(LeaveTypeReqDto leaveTypeReqDto, String Id) {
        LeaveType leaveType1 = getLeave(Id);
        leaveType1.setLeaveDes(leaveTypeReqDto.getLeaveDes());
        leaveType1.setLeaveTitle(leaveTypeReqDto.getLeaveTitle());
        leaveType1.setLeaveDayPerYear(leaveTypeReqDto.getLeaveDayPerYear());
        return leaveTypeRepo.save(leaveType1);
    }

    @Override
    public LeaveType getLeave(String Id) {
        return leaveTypeRepo.findById(Id).orElseThrow(() ->
                new IllegalArgumentException(
                        "leave type with id: " + Id + " could not be found"));
    }

    @Override
    public List<LeaveType> getListLeave() {
        return leaveTypeRepo.findAll();
    }

    @Override
    public void delete(String Id) {
        LeaveType leaveType = getLeave(Id);
        leaveTypeRepo.delete(leaveType);
    }
}
