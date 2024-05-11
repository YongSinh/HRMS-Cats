package com.cats.payrollservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    @JsonManagedReference
    @OneToMany(mappedBy = "allowances", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<EmployeeAllowances> employeeAllowances = new ArrayList<>();

}
