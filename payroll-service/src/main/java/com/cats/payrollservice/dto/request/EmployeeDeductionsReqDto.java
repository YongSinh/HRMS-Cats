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
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeDeductionsReqDto {
    private List<Long> deductions;
    private Integer type;
    private Double amount;
    private LocalDate effectiveDate;
    private LocalDateTime dateCreated;
    private LocalDate paySlipDate;
}
