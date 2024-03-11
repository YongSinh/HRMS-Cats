package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.LeaveBalanceDtoRep;
import com.cats.attendanceservice.dto.LeaveTypeReqDto;
import com.cats.attendanceservice.dto.mapper;
import com.cats.attendanceservice.model.LeaveBalance;
import com.cats.attendanceservice.repository.LeaveBalanceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveBalanceServiceImp implements LeaveBalanceService{
    private final LeaveBalanceRepo leaveBalanceRepo;

    @Override
    public LeaveBalance create(LeaveTypeReqDto leaveTypeReqDto) {
        return null;
    }

    @Override
    public LeaveBalance edit(LeaveTypeReqDto leaveTypeReqDto, Long Id) {
        return null;
    }

    @Override
    public LeaveBalance getLeaveBalance(Long Id) {
        return leaveBalanceRepo.findById(Id).orElseThrow(() ->
                new IllegalArgumentException(
                        "leave type with id: " + Id + " could not be found"));
    }

    @Override
    public LeaveBalanceDtoRep getLeaveBalanceById(Long Id) {
        LeaveBalance leaveBalance =getLeaveBalance(Id);
        return mapper.leaveBalanceToBookResponseDto(leaveBalance);
    }

    @Override
    public List<LeaveBalanceDtoRep> getListLeaveBalance() {
        return mapper.leaveBalanceToBookResponseDtos(leaveBalanceRepo.findAll());
    }
}
