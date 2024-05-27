package com.cats.payrollservice.repository;

import com.cats.payrollservice.model.EmployeeDeductions;
import com.cats.payrollservice.model.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PayslipRepo extends JpaRepository<Payslip, Long> {
    List<Payslip> findByEmpId(Long emId);
    Payslip findByEmpIdAndDateCreated(Long empId, LocalDateTime dateCreated);
}
