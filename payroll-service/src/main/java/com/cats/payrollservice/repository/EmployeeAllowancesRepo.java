package com.cats.payrollservice.repository;

import com.cats.payrollservice.model.EmployeeAllowances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeAllowancesRepo extends JpaRepository<EmployeeAllowances, Long> {
    List<EmployeeAllowances> findByEmpId(Long emId);
    List<EmployeeAllowances> findAllByOrderByEmpIdDesc();
    List<EmployeeAllowances> findByEmpIdIn(Collection<Long> empId);
    List<EmployeeAllowances> findByPaySlipId(Long paySlipId);
    Optional<EmployeeAllowances> findByEmpIdAndDateCreated(Long empId, LocalDateTime dateCreated);
    Optional<EmployeeAllowances> findByEmpIdAndEffectiveDate(Long empId, LocalDate effectiveDate);
}
