package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.*;
import com.cats.attendanceservice.model.LeaveBalance;
import com.cats.attendanceservice.model.LeaveType;
import com.cats.attendanceservice.repository.LeaveBalanceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveBalanceServiceImp implements LeaveBalanceService{
    private final LeaveBalanceRepo leaveBalanceRepo;
    private final  LeaveTypeService leaveTypeService;

    @Override
    public  List<LeaveBalance> create(LeaveBalanceListDtoReq leaveBalanceListDtoReq) {
        List<LeaveBalance> leaveBalances = new ArrayList<>();
        if(leaveBalanceListDtoReq.getLeaveType().isEmpty()){
            throw new IllegalArgumentException("leave type is empty latest on leave Balances");
        }
        for (String typeId : leaveBalanceListDtoReq.getLeaveType()){
        for (Long emId : leaveBalanceListDtoReq.getEmpId()){
            LeaveBalance leaveBalance = new LeaveBalance();
            leaveBalance.setEmpId(emId);
            leaveBalance.setLastUpdateDate(leaveBalanceListDtoReq.getLastUpdateDate());
            LeaveType leaveType2 = leaveTypeService.getLeave(typeId);
            leaveBalance.setLeaveType(leaveType2);
            leaveBalance.setBalanceAmount(leaveType2.getLeaveDayPerYear());
            leaveBalances.add(leaveBalance);
        }
        }

            return leaveBalanceRepo.saveAll(leaveBalances);
        }

    @Override
    public  LeaveBalance edit(LeaveBalanceRepDto leaveBalanceRepDto, Long Id) {
        LeaveBalance leaveBalance = getLeaveBalance(Id);
        leaveBalance.setEmpId(leaveBalanceRepDto.getEmpId());
        leaveBalance.setLastUpdateDate(leaveBalanceRepDto.getLastUpdateDate());
        leaveBalance.setBalanceAmount(leaveBalanceRepDto.getBalanceAmount());
        if (leaveBalanceRepDto.getLeaveType() != null){
            LeaveType leaveType2 = leaveTypeService.getLeave(leaveBalanceRepDto.getLeaveType());
            leaveBalance.setLeaveType(leaveType2);
        }
        return leaveBalanceRepo.save(leaveBalance);
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
    public List<LeaveBalanceDtoRep> getLeaveBalanceByEmId(Long emId) {
        return mapper.leaveBalanceToBookResponseDtos(leaveBalanceRepo.findByEmpId(emId));
    }

    @Override
    public List<LeaveBalanceDtoRep> getListLeaveBalance() {
        return mapper.leaveBalanceToBookResponseDtos(leaveBalanceRepo.findAll());
    }

    @Override
    public LeaveBalanceDtoRep getLeaveBalanceByLeaveTypeAndEmpId(String leaveType, Long emId) {
        return mapper.leaveBalanceToBookResponseDto(leaveBalanceRepo.findLeaveBalanceByLeaveTypeAndEmpId(leaveType, emId));
    }

    @Override
    public LeaveBalance editLeaveBalance(String leaveType, Long emId, Long newBalance) {
        LeaveBalance leaveBalance = leaveBalanceRepo.findLeaveBalanceByLeaveTypeAndEmpId(leaveType, emId);
        leaveBalance.setBalanceAmount(leaveBalance.getBalanceAmount() - newBalance);
        leaveBalanceRepo.save(leaveBalance);
        return leaveBalance;
    }

    @Override
    public void delete(Long Id) {
        LeaveBalance leaveBalance = getLeaveBalance(Id);
        leaveBalanceRepo.delete(leaveBalance);
    }

    @Override
    public void deleteByLeaveType(String Id) {
        LeaveBalance leaveBalance = leaveBalanceRepo.findByLeaveType_Id(Id);
        leaveBalanceRepo.delete(leaveBalance);
    }
}
