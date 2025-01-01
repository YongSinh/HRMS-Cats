package com.cats.payrollservice.repository;

import com.cats.payrollservice.non_entity_POJO.PaySlipReportDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



import java.util.List;

@Repository
public interface PayslipReprotRepo extends JpaRepository<PaySlipReportDto, String> {

    @Procedure(procedureName = "GetFirstAndSecondPayments")
    List<PaySlipReportDto> getFirstAndSecondPayments(@Param("ref_no") String ref_no);

}
