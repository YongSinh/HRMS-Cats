package com.cats.payrollservice.repository;

import com.cats.payrollservice.model.EmployeeDeductions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeDeductionsRepo extends JpaRepository<EmployeeDeductions, Long> {
    List<EmployeeDeductions> findByEmpId(Long emId);
    List<EmployeeDeductions> findByEmpIdAndEffectiveDateGreaterThanEqual(Long empId, LocalDate effectiveDate);
    List<EmployeeDeductions> findByPaySlipId(Long paySlipId);
    Optional<EmployeeDeductions> findByEmpIdAndDateCreated(Long empId, LocalDateTime dateCreated);
    Optional<EmployeeDeductions> findByEmpIdAndEffectiveDate(Long empId, LocalDate effectiveDate);
}
