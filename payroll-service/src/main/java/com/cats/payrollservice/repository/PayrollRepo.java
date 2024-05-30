package com.cats.payrollservice.repository;

import com.cats.payrollservice.model.EmployeeDeductions;
import com.cats.payrollservice.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PayrollRepo extends JpaRepository<Payroll, Long> {
    Payroll findPayrollByEmpIdAndDateCreate(Long empId, LocalDate dateCreate);
    List<Payroll> findPayrollByEmpId(Long empId);
    List<Payroll> findByEmpIdInOrderByDateCreate(List<Long> empIds);

}
