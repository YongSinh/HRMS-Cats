package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.LeaveTypeReqDto;
import com.cats.attendanceservice.model.LeaveType;
import org.springframework.stereotype.Service;

import java.util.List;
public interface LeaveTypeService {
    LeaveType create(LeaveTypeReqDto leaveTypeReqDto);
    LeaveType edit(LeaveTypeReqDto leaveTypeReqDto, String Id);
    LeaveType getLeave(String Id);
    List<LeaveType> getListLeave();
    void delete(String Id);
    void updateLeaveBalance(String leaveTypeId, Long newValue);
}
