package com.cats.payrollservice.repository;

import com.cats.payrollservice.model.EmployeeDeductions;
import com.cats.payrollservice.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface PayrollRepo extends JpaRepository<Payroll, Long> {
    Payroll findPayrollByEmpIdAndDateCreate(Long empId, LocalDate dateCreate);
    List<Payroll> findPayrollByEmpIdOrderByDateCreateDesc(Long empId);
    List<Payroll> findByEmpIdInOrderByDateCreate(List<Long> empIds);

    List<Payroll> findByDateCreate(LocalDate dateCreate);
    List<Payroll> findAllByOrderByDateCreateDesc();

    @Modifying
    @Query("UPDATE Payroll p SET p.status = 2 WHERE p.dateTo < :currentDate AND p.status <> 2")
    void updateStatusToComputed(@Param("currentDate") LocalDate currentDate);

}
