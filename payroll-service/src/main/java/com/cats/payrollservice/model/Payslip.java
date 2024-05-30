package com.cats.payrollservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "payslip")
@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Payslip {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name ="id")
    private Long Id;
    @ManyToOne( fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    @JoinColumn( name ="payroll_id")
    private Payroll payroll;
    @Column(name ="employee_id")
    private Long empId;
    @Column(name ="present")
    private Integer present;
    @Column(name ="absent")
    private Integer absent;
    @Column(name ="payType")
    private Integer payType;
    @Column(name ="salary")
    private Double salary;
    @Column(name ="net")
    private Double net;
    @Column(name ="allowance_amount")
    private Double allowanceAmount;
    @Column(name ="allowances",columnDefinition = "text")
    private String allowances;
    @Column(name ="deduction_amount")
    private Double deductionAmount;
    @Column(name ="deductions",columnDefinition = "text")
    private String deductions;
    @Column(name ="date_created")
    private LocalDateTime dateCreated;
}
