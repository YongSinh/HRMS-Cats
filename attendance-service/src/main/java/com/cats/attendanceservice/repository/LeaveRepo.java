package com.cats.attendanceservice.repository;

import com.cats.attendanceservice.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface LeaveRepo extends JpaRepository<Leave,Long> {
    List<Leave> findLeaveByEmpId(Long empId);
    List<Leave> findByEmpIdIn(Collection<Long> empId);
    List<Leave> findByEmpIdAndCreatedAtOrderByCreatedAtDesc(Long empId, LocalDate createdAt);
    List<Leave> findByCreatedAtBetween(LocalDate createdAt, LocalDate createdAt2);
    List<Leave> findByCreatedAtOrderByCreatedAtDesc(LocalDate createdAt);
    List<Leave> findByEmpIdAndCreatedAtBetween(Long empId, LocalDate createdAt, LocalDate createdAt2);
    @Query(value = "select * from `leave` where id = ?1 ORDER BY created_at ;", nativeQuery = true)
    List<Leave> findLeaveByEmpIdAndOOrderByDate(Long empId);
}
