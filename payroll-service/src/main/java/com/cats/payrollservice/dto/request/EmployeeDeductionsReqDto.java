package com.cats.payrollservice.dto.request;

import com.cats.payrollservice.model.Deductions;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeDeductionsReqDto {
    private Long empId;
    private Long deductions;
    private Integer type;
    private Double amount;
    private LocalDate effectiveDate;
    private LocalDateTime dateCreated;
}
