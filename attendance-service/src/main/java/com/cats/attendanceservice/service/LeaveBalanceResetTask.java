package com.cats.attendanceservice.service;

import com.cats.attendanceservice.model.LeaveBalance;
import com.cats.attendanceservice.model.LeaveType;
import com.cats.attendanceservice.repository.LeaveBalanceRepo;
import com.cats.attendanceservice.repository.LeaveTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class  LeaveBalanceResetTask {
    private final LeaveTypeRepo leaveTypeRepo;
    private final LeaveBalanceRepo leaveBalanceRepo;

    @Scheduled(cron = "0 0 0 1 1 *") // Run at midnight on January 1st annually
    public void resetLeaveBalances() {
        // Get all leave types
        List<LeaveType> leaveTypes = leaveTypeRepo.findAll();

        for (LeaveType leaveType : leaveTypes) {
            // Get leave balances for the current leave type
            List<LeaveBalance> leaveBalances = leaveBalanceRepo.findByLeaveType(leaveType);

            // Reset leave balances based on leave day per year
            for (LeaveBalance balance : leaveBalances) {
                long leaveDaysPerYear = leaveType.getLeaveDayPerYear();
                balance.setBalanceAmount(leaveDaysPerYear);
            }

            // Save the updated leave balances back to the database
            leaveBalanceRepo.saveAll(leaveBalances);
        }
    }

}
