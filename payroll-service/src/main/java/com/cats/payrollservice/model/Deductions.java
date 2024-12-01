package com.cats.payrollservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    @JsonManagedReference
    @OneToMany(mappedBy = "deductions",
            cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            fetch = FetchType.LAZY)
    private List<EmployeeDeductions> employeeDeductions = new ArrayList<>();
}
