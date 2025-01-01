package com.cats.payrollservice.repository;

import com.cats.payrollservice.model.Deductions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeductionsRepo extends JpaRepository<Deductions, Long> {

}
