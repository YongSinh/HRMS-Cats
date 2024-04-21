package com.cats.payrollservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Table(name = "tax")
@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Tax {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name ="id")
    private Long id;
    @Column(name ="taxableSalary")
    private String taxableSalary;
    @Column(name ="rate")
    private Double rate;
}
