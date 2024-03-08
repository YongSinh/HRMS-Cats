package com.cats.attendanceservice.service;

import com.cats.attendanceservice.repository.LeaveBalanceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaveBalanceServiceImp implements LeaveBalanceService{
    private final LeaveBalanceRepo leaveBalanceRepo;
}
