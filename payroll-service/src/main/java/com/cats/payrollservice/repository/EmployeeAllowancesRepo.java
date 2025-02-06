package com.cats.payrollservice.repository;

import com.cats.payrollservice.model.EmployeeAllowances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeAllowancesRepo extends JpaRepository<EmployeeAllowances, Long> {
    List<EmployeeAllowances> findByEmpId(Long emId);

    List<EmployeeAllowances> findAllByOrderByEmpIdDesc();

    List<EmployeeAllowances> findByPaySlipId(Long paySlipId);

    @Query("SELECT e FROM EmployeeAllowances e WHERE e.effectiveDate BETWEEN :startOfMonth AND :endOfMonth And e.empId = :emId")
    List<EmployeeAllowances> findByEffectiveDateForCurrentMonth(
            @Param("startOfMonth") LocalDate startOfMonth,
            @Param("endOfMonth") LocalDate endOfMonth,
            @Param("emId") Long emId
    );
}
