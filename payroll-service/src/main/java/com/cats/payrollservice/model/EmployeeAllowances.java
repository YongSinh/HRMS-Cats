package com.cats.payrollservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "employee_allowances")
@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeAllowances {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name ="id")
    private Long empAllId;
    @Column(name ="employee_id")
    private Long empId;
    @ManyToOne( fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    @JoinColumn(name = "allowance_id")
    private Allowances allowances;
    @Column(name ="type")
    private Integer type;
    @Column(name ="amount")
    private Double amount;
    @Column(name ="effective_date")
    private LocalDate effectiveDate;
    @Column(name ="date_created")
    private LocalDateTime dateCreated;

}
