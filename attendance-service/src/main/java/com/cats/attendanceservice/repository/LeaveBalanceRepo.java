package com.cats.attendanceservice.repository;

import com.cats.attendanceservice.model.LeaveBalance;
import com.cats.attendanceservice.model.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveBalanceRepo extends JpaRepository<LeaveBalance, Long> {
    List<LeaveBalance> findByEmpId(Long empId);
    @Query(value = "select * from leave_balance lb where leave_type_id = ?1", nativeQuery = true)
    LeaveBalance findByLeaveType_Id(String leaveType_id);
    @Query(value = "select * from leave_balance lb where leave_type_id = ?1 and empid = ?2", nativeQuery = true)
    LeaveBalance findLeaveBalanceByLeaveTypeAndEmpId(String leaveType, Long empId);

}
