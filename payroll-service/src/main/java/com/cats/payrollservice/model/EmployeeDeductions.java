package com.cats.payrollservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Table(name = "employee_deductions")
@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeDeductions {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name ="id")
    private Long empDedId;
    @Column(name ="employee_id")
    private Long empId;
    @Column(name ="deductions_id")
    private Long dedId;
    @Column(name ="type")
    private Integer type;
    @Column(name ="amount")
    private Double amount;
    @Column(name ="effective_date")
    private LocalDate effectiveDate;
    @Column(name ="date_created")
    private LocalDateTime dateCreated;
}