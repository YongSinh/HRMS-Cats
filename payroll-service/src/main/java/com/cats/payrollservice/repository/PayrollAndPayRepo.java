package com.cats.payrollservice.repository;

import com.cats.payrollservice.non_entity_POJO.PaySlipReportDto;
import com.cats.payrollservice.non_entity_POJO.PayrollAndPaySlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PayrollAndPayRepo extends JpaRepository<PayrollAndPaySlip, String> {
    @Procedure(procedureName = "GetPayrollByRefNo2")
    PayrollAndPaySlip GetPayrollByRefNo2(@Param("ref_no") String ref_no);
    @Procedure(procedureName = "GetPayrollByCreateDate")
    List<PayrollAndPaySlip> GetPayrollByCreateDate(@Param("createDate") LocalDate createDate);
    @Procedure(procedureName = "GetPayrollWithTaxForUser")
    List<PayrollAndPaySlip> GetPayrollWithTaxForUser(@Param("emId") Long emId);
    @Procedure(procedureName = "GetListPayroll")
    List<PayrollAndPaySlip> GetListPayroll();
}
