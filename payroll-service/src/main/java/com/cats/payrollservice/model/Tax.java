package com.cats.payrollservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @JsonManagedReference
    @OneToMany(mappedBy = "tax", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<Salaries> salariesList = new ArrayList<>();

}
