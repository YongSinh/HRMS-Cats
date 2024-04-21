package com.cats.payrollservice.repository;

import com.cats.payrollservice.model.EmployeeDeductions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDeductionsRepo extends JpaRepository<EmployeeDeductions, Long> {
}
