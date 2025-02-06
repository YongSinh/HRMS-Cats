package com.cats.payrollservice.repository;

import com.cats.payrollservice.model.EmployeeDeductions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeDeductionsRepo extends JpaRepository<EmployeeDeductions, Long> {
    List<EmployeeDeductions> findByEmpId(Long emId);

    List<EmployeeDeductions> findByPaySlipId(Long paySlipId);

    List<EmployeeDeductions> findAllByOrderByEmpDedId();

    @Query("SELECT e FROM EmployeeDeductions e WHERE e.effectiveDate BETWEEN :startOfMonth AND :endOfMonth And e.empId = :emId")
    List<EmployeeDeductions> findByEffectiveDateForCurrentMonth(
            @Param("startOfMonth") LocalDate startOfMonth,
            @Param("endOfMonth") LocalDate endOfMonth,
            @Param("emId") Long emId
    );
}
