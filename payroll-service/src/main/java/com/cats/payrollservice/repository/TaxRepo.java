package com.cats.payrollservice.repository;

import com.cats.payrollservice.model.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxRepo extends JpaRepository<Tax, Long> {

    List<Tax> findByUpperLimitGreaterThanEqualOrderByLowerLimitAsc(Double salary);

    @Query(value = "select * from Tax where :salary BETWEEN lowerLimit AND upperLimit", nativeQuery = true)
    Tax findRateBySalary(@Param("salary") Double salary);

}
