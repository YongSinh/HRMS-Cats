package com.cats.payrollservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "salaries")
@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Salaries {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name ="id")
    private Long id;
    @Column(name ="empId")
    private Long empId;
    @Column(name ="tax_id")
    private Long taxId;
    @Column(name ="salary")
    private Double salary;
    @Column(name ="from_date")
    private LocalDate fromDate;
    @Column(name ="to_date")
    private LocalDate toDate;
}
