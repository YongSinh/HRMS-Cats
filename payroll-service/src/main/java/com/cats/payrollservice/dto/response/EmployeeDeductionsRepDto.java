package com.cats.payrollservice.dto.response;

import com.cats.payrollservice.model.Deductions;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeDeductionsRepDto {
    private Long empDedId;
    private Long empId;
    private Long paySlipId;
    private String deductions;
    private Long deductionsId;
    private Integer type;
    private Double amount;
    private LocalDate effectiveDate;
    private LocalDateTime dateCreated;
}
