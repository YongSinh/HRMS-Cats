package com.cats.payrollservice.repository;

import com.cats.payrollservice.model.EmployeeDeductions;
import com.cats.payrollservice.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollRepo extends JpaRepository<Payroll, Long> {
}