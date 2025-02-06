package com.cats.payrollservice.repository;

import com.cats.payrollservice.model.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface PayslipRepo extends JpaRepository<Payslip, Long> {
    List<Payslip> findByEmpId(Long emId);

    List<Payslip> findByEmpIdIn(Collection<Long> empId);

    List<Payslip> findByDateCreatedBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Payslip> findByOrderByIdDesc();

    boolean existsByEmpIdAndPayTypeAndDateCreatedBetween(
            @Param("empId") Long empId,
            @Param("payType") int payType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query(nativeQuery = true, value = "SELECT * FROM payslip WHERE employee_id = :empId AND DATE(date_created) = :dateCreated")
    Payslip findByEmpIdAndDateCreated(@Param("empId") Long empId, @Param("dateCreated") LocalDate dateCreated);
}
