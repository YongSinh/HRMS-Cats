package com.cats.payrollservice.repository;

import com.cats.payrollservice.model.EmployeeDeductions;
import com.cats.payrollservice.model.Salaries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface SalariesRepo extends JpaRepository<Salaries, Long> {
    Salaries findByEmpId(Long empId);
    List<Salaries> findByEmpIdIn(Collection<Long> empId);
    boolean existsByEmpIdAndFromDateLessThanEqualAndToDateGreaterThanEqual(Long empId, LocalDate fromDate, LocalDate toDate);

}
