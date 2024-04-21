package com.cats.payrollservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Table(name = "allowances")
@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Allowances {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name ="id")
    private Long aLLId;
    @Column(name ="allowances")
    private String allowances;
    @Column(name ="description")
    private String description;
}
