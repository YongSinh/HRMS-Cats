package com.cats.attendanceservice.repository;

import com.cats.attendanceservice.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface LeaveRepo extends JpaRepository<Leave,Long> {
    List<Leave> findLeaveByEmpIdOrderByCreatedAtDesc(Long empId);
    List<Leave> findAllByOrderByCreatedAtDesc();
    List<Leave> findAllByOrderByCreatedAtDescLeaveIdDesc();

    List<Leave> findByEmpIdInOrderByCreatedAtDesc(Collection<Long> empId);
    List<Leave> findByEmpIdAndCreatedAtOrderByCreatedAtDesc(Long empId, LocalDate createdAt);
    List<Leave> findByCreatedAtBetween(LocalDate createdAt, LocalDate createdAt2);
    List<Leave> findByCreatedAtOrderByCreatedAtDesc(LocalDate createdAt);
    List<Leave> findByEmpIdAndCreatedAtBetween(Long empId, LocalDate createdAt, LocalDate createdAt2);
    @Query(value = "select * from `leave` where id = ?1 ORDER BY created_at ;", nativeQuery = true)
    List<Leave> findLeaveByEmpIdAndOOrderByDate(Long empId);

    @Query("SELECT l FROM Leave l WHERE l.empId = :empId " +
            "AND l.cancelled = false " +
            "AND (l.startDate <= :endDate AND l.endDate >= :startDate)")
    List<Leave> findOverlappingLeaves(
            @Param("empId") Long empId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
