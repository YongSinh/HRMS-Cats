package com.cats.informationmanagementservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "department")
@RequiredArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "depId")
    private Long depId;
    @Column(name = "depName")
    private String depName;
    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "department", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "department", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<Position> positions = new ArrayList<>();
}
