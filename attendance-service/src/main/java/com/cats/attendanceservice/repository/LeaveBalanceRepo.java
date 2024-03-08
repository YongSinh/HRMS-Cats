package com.cats.attendanceservice.repository;

import com.cats.attendanceservice.model.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveBalanceRepo extends JpaRepository<LeaveBalance, Long> {
}
