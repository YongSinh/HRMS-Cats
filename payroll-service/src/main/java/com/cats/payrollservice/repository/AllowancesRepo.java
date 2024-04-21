package com.cats.payrollservice.repository;

import com.cats.payrollservice.model.Allowances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowancesRepo extends JpaRepository<Allowances, Long> {
}
