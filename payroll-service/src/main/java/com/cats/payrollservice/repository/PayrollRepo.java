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
import java.util.Optional;

@Repository
public interface PayrollRepo extends JpaRepository<Payroll, Long> {
    Payroll findPayrollByEmpIdAndDateCreate(Long empId, LocalDate dateCreate);
    List<Payroll> findPayrollByEmpIdOrderByDateCreateDesc(Long empId);
    List<Payroll> findByEmpIdInOrderByDateCreate(List<Long> empIds);

    List<Payroll> findByDateCreateBetween(LocalDate dateCreate, LocalDate dateCreate2);
    List<Payroll> findByEmpIdAndDateCreateBetween(Long empId, LocalDate dateCreate, LocalDate dateCreate2);

    List<Payroll> findByDateCreate(LocalDate dateCreate);
    List<Payroll> findAllByOrderByDateCreateDesc();
    @Query(
            value = """
            SELECT * FROM payroll e
            WHERE (e.date_from <= :endOfMonth AND e.date_to >= :startOfMonth)
            AND e.empId =:empId
            ORDER BY e.date_from DESC
            LIMIT 1
            """,
            nativeQuery = true
    )
    Payroll findByCurrentMonthAndEmpIds(
            @Param("startOfMonth") LocalDate startOfMonth,
            @Param("endOfMonth") LocalDate endOfMonth,
            @Param("empId") Long empId
    );

    boolean existsByEmpIdAndDateCreateBetween(Long empId, LocalDate dateCreate, LocalDate dateCreate2);
    @Modifying
    @Query("UPDATE Payroll p " +
            "SET p.status = 2 " +
            "WHERE p.status <> 2 " +
            "AND FUNCTION('MONTH', p.dateCreate) = FUNCTION('MONTH', :currentDate) " +
            "AND FUNCTION('YEAR', p.dateCreate) = FUNCTION('YEAR', :currentDate)")
    void updateStatusToComputed(@Param("currentDate") LocalDate currentDate);

}
