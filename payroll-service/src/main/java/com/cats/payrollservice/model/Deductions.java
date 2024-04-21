package com.cats.payrollservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Table(name = "deductions")
@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Deductions {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name ="id")
    private Long deId;
    @Column(name ="deduction")
    private String deduction;
    @Column(name ="description")
    private String description;
}
