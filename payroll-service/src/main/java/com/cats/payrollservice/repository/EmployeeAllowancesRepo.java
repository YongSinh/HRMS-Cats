package com.cats.payrollservice.repository;

import com.cats.payrollservice.model.EmployeeAllowances;
import com.cats.payrollservice.model.EmployeeDeductions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeAllowancesRepo extends JpaRepository<EmployeeAllowances, Long> {
    List<EmployeeAllowances> findByEmpId(Long emId);
    List<EmployeeAllowances> findByPaySlipId(Long paySlipId);
}
